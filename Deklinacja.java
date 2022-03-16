import java.io.Serializable;

public class Deklinacja implements Serializable
    {
        private double stopnie;
        private double minuty;
        private double sekundy;

        public double getStopnie()
        {
            return stopnie;
        }

        public double getMinuty()
        {
            return minuty;
        }

        public double getSekundy()
        {
            return sekundy;
        }

        //konstruktor sprawdzający poprawność podanych wartości
        public Deklinacja(double stopnie, double minuty, double sekundy)
        {
            if(stopnie <= 90 && stopnie >= -90)
            {
                this.stopnie = stopnie;
            }
            else
            {
                throw new IllegalArgumentException(); 
            }
            
            if((minuty > 0 && minuty < 60) && (stopnie < 90 && stopnie > -90))
            {
                this.minuty = minuty;
            }
            else
            {
                throw new IllegalArgumentException();
            }

            if((sekundy >0 && sekundy <60) && (stopnie < 90 && stopnie > -90))
            {
                this.sekundy = sekundy;
            }
            else
            {
                throw new IllegalArgumentException();
            }
        }

        //metoda wyświetlająca wartości deklinacji
        public void Wyswietl()
        {
            System.out.print("Deklinacja : ");
            System.out.print(String.format("%s stopni ",stopnie));
            System.out.print(String.format("%s minut ",minuty));
            System.out.println(String.format("%s sekund ",sekundy));
        }
    }