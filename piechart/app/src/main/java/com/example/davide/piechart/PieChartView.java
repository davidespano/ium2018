package com.example.davide.piechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * Classe che rappresenta un grafico a torta
 */
public class PieChartView extends View {


    /**
     * Centro del grafico, ricalcolato a ogni disegno
     */
    private PointF center = new PointF();
    /**
     * Rettangolo che contiene il grafico a torta
     */
    private RectF enclosing = new RectF();
    /**
     * Variabile di appoggio per il disegno dell'elemento selezionato
     */
    private float selectedStartAngle = 0.0f;

    /**
     * Colore di sfondo del controllo
     */
    private int backgroundColor = Color.WHITE;
    /**
     * Lista delle percentuali (float) da disegnare come fette della torta
     */
    private List<Float> percent;
    /**
     * Lista dei colori per ogni fetta della torta
     */
    private List<Integer> segmentColor;
    /**
     * Colore del bordo delle fette non selezionate
     */
    private int strokeColor;
    /**
     * Spessore del bordo delle fette non selezionate
     */
    private int strokeWidth;
    /**
     * Colore del bordo della fetta selezionata
     */
    private int selectedColor;
    /**
     * Spessore del bordo della fetta selezionata
     */
    private int selectedWidth = 8;
    /**
     * Indice della fetta selezionata nella lista delle percentuali
     */
    private int selectedIndex = 2;
    /**
     * Raggio della torta
     */
    private int radius = 100;

    /**
     * Costruttore del controllo
     * @param context Il contesto grafico su cui disegnare
     */
    public PieChartView(Context context) {
        super(context);
    }

    /**
     * Costrutture del controllo
     * @param context Il contesto grafico su cui disegnare
     * @param attrs Gli attributi ricevuti dall'activity
     */
    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Costrutture del controllo
     * @param context Il contesto grafico su cui disegnare
     * @param attrs Gli attributi ricevuti dall'activity
     * @param defStyle Stili di disegno di default
     */
    public PieChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Restituisce il colore di sfondo
     * @return Il colore di sfondo
     */
    public int getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Imposta il colore di sfondo
     * @param backgroundColor Il colore di sfondo
     */
    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Restituisce la lista delle percentuali da disegnare nel grafico,
     * @return La lista delle percentuali
     */
    public List<Float> getPercent() {
        return percent;
    }

    /**
     * Imposta la lista delle percentuali da disegnare nel grafico
     * @param percent La lista delle percentuali
     */
    public void setPercent(List<Float> percent) {
        this.percent = percent;
    }

    /**
     * Restituisce la lista dei colori con cui si disegnano le fette. L'indice dell'i-esimo colore
     * corrisponde alla i-esima percentuale nella lista delle percentuali.
     * @return
     */
    public List<Integer> getSegmentColor() {
        return segmentColor;
    }

    /**
     * Imposta la lista dei colori con cui si disegnano le fette. La lista deve avere
     * la stessa dimensione di quella delle percentuali
     * @param segmentColor la lista dei colori
     * @throws IllegalArgumentException se la lista dei colori e delle percentuali hanno dimensione diversa
     */
    public void setSegmentColor(List<Integer> segmentColor) {
        if(segmentColor.size() != percent.size()){
            throw  new IllegalArgumentException(
                    "La lista dei colori e delle percentuali devono avere la stessa dimensione");
        }
        this.segmentColor = segmentColor;
    }

    /**
     * Restituisce il raggio della torta
     * @return Il raggio della torta
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Imposta il raggio della torta
     * @param radius Il raggio della torta
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Restituisce il colore del bordo delle fette
     * @return il colore del bordo delle fette
     */
    public int getStrokeColor() {
        return strokeColor;
    }

    /**
     * Imposta il colore del bordo delle fette
     * @param strokeColor il colore del bordo delle fette
     */
    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    /**
     * Restituisce la dimensione del bordo delle fette
     * @return La dimensione del bordo delle fette
     */
    public int getStrokeWidth() {
        return strokeWidth;
    }

    /**
     * Imposta la dimensione del bordo delle fette
     * @param strokeWidth la dimensione del bordo delle fette
     */
    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    /**
     * Restituisce il colore del bordo selezionato
     * @return Il colore del bordo selezionato
     */
    public int getSelectedColor() {
        return selectedColor;
    }

    /**
     * Imposta il colore del bordo selezionato
     * @param selectedColor Il colore del bordo selezionato
     */
    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    /**
     * Restituisce la dimensione del bordo dell'elemento selezionato
     * @return la dimensione del bordo dell'elemento selezionato
     */
    public int getSelectedWidth() {
        return selectedWidth;
    }

    /**
     * Imposta la dimensione del bordo dell'elemento selezionato
     * @param selectedWidth la dimensione del bordo dell'elemento selezionato
     */
    public void setSelectedWidth(int selectedWidth) {
        this.selectedWidth = selectedWidth;
    }

    /**
     * Procedura di disegno del controllo
     * @param canvas Il contesto grafico su cui disegnare
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // utilizziamo questo oggetto per definire
        // colori, font, tipi di linee ecc
        Paint paint = new Paint();

        // impostiamo l'antialiasing
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        // cancelliamo lo sfondo
        paint.setColor(this.getBackgroundColor());
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);


        // angolo dal quale si inizia a disegnare
        float alpha = -90.0f;

        // 1% in radianti
        float p2a = 360.0f / 100.0f;

        // calcolo il centro del cerchio e le dimensioni del quadrato in cui inscriverlo
        center.x = canvas.getWidth() / 2;
        center.y = canvas.getHeight() / 2;
        enclosing.top = center.y - radius;
        enclosing.bottom = center.y + radius;
        enclosing.left = center.x - radius;
        enclosing.right = center.x + radius;

        float p;
        int c;

        // disegno la parte colorata (il fill)
        for (int i = 0; i < percent.size(); i++) {
            // la percentuale da rappresentare
            p = percent.get(i);
            // il colore da usare
            c = segmentColor.get(i);
            paint.setColor(c);
            paint.setStyle(Paint.Style.FILL);

            // il disegno parte dall'angolo alpha e disegna un segmento circolare di ampiezza
            // p * p2a. Disegna in senso orario (al contrario dell'andamento degli angoli
            // nella circonferenza unitaria usuale).
            canvas.drawArc(
                    enclosing,
                    alpha,
                    p * p2a,
                    true,
                    paint);

            alpha += p * p2a;
        }

        // disegno il contorno (lo stroke)
        alpha = -90.0f;
        for (int i = 0; i < percent.size(); i++) {
            // la percentuale da rappresentare
            p = percent.get(i);
            // il colore da usare
            c = segmentColor.get(i);
            paint.setColor(strokeColor);
            paint.setStrokeWidth(strokeWidth);
            paint.setStyle(Paint.Style.STROKE);

            // salvo l'angolo dell'elemento selezionato per il passo successivo
            if(i == selectedIndex){
                selectedStartAngle = alpha;
            }

            canvas.drawArc(
                    enclosing,
                    alpha,
                    p * p2a,
                    true,
                    paint);

            alpha += p * p2a;
        }

        // disegno il contorno dell'item selezionato
        if(selectedIndex >= 0 && selectedIndex < percent.size()) {
            // il valore selezionato e' valido
            paint.setColor(selectedColor);
            paint.setStrokeWidth(selectedWidth);
            paint.setStyle(Paint.Style.STROKE);

            canvas.drawArc(
                    enclosing,
                    selectedStartAngle,
                    percent.get(selectedIndex) * p2a,
                    true,
                    paint);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        // ottento le coordinate del tocco dal descrittore dell'evento
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                // prima posizione del primo tocco
                if (event.getPointerCount() == 1) {
                    // l'indice selezionato è quello relativo alla fetta che contiene il
                    // il punto del tocco.
                    selectedIndex = this.pickCorrelation(x, y);
                    // richiedo di aggiornare il disegno
                    this.invalidate();
                    return true;
                }
                break;
        }

        return false;
    }

    /**
     * Restituisce l'indice della fetta di torta che contiene il punto di coordinate (x,y)
     * @param x L'ascissa del punto
     * @param y L'ordinata del punto
     * @return l'indice della fetta di torta
     */
    private int pickCorrelation(float x, float y){
       if(enclosing.contains(x, y)){
           // sottraggo alla x e alla y le coordinate del centro
           float dx = x - center.x;
           float dy = y - center.y;

           // ottengo la distanza dal centro
           float r = (float) Math.sqrt(dx * dx + dy * dy);

           float cos = dx/r;
           float sin = - dy/r;

           double angle = Math.toDegrees(Math.atan2(sin, cos));

           Log.d("ANGLE", "angle: " + angle + " cos " + cos + " sin " + sin);

           if(angle > 90 && angle < 360){
               angle = angle - 360;
           }

           float alpha = 90.0f;
           float alpha1;

           // 1% in radianti
           float p2a = 360.0f / 100.0f;
           float p;
           for(int i = 0; i<percent.size(); i++){
               p = percent.get(i);
               alpha1 =  alpha - p * p2a;
               if(angle > alpha1 && angle < alpha){
                   return i;
               }
               alpha = alpha1;

           }

       }else{
           return -1;
       }
       return 1;
    }




}
