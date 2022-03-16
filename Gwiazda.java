import java.io.Serializable;
import java.time.LocalTime;

public class Gwiazda implements Serializable
{
    private String nazwa;
    private String nazwaKatalogowa;
    private Deklinacja deklinacja;
    private LocalTime rektascensja;
    private double obserwowanaWielkoscGwiazdowa;
    private double absolutnaWielkoscGwiazdowa;
    private double odleglosc;
    private String gwiazdozbior;
    private String polkula;
    private double temperatura;
    private double masa;

    public String getNazwa()
    {
        return nazwa;
    }

    public String getNazwaKatalogowa()
    {
        return nazwaKatalogowa;
    }

    public void setNazwaKatalogowa(String nazwaKatalogowa)
    {
        this.nazwaKatalogowa = nazwaKatalogowa;
    }

    public double getObserwowanaWielkoscGwiazdowa()
    {
        return obserwowanaWielkoscGwiazdowa;
    }

    public double getAbsolutnaWielkoscGwiazdowa()
    {
        return absolutnaWielkoscGwiazdowa;
    }

    public double getOdleglosc()
    {
        return odleglosc;
    }

    public String getGwiazdozbior()
    {
        return gwiazdozbior;
    }

    public String getPolkula()
    {
        return polkula;
    }

    public double getTemperatura()
    {
        return temperatura;
    }

    public double getMasa()
    {
        return masa;
    }


    //konstruktor, który sprawdza czy podane wartości są dopuszczalne
    public Gwiazda(String nazwa,Deklinacja d,LocalTime r,double obserwowanaWielkoscGwiazdowa, 
    double odleglosc, String gwiazdozbior, double temperatura, double masa)
    {
        this.nazwa = nazwa;
        this.deklinacja = new Deklinacja(d.getStopnie(), d.getMinuty(), d.getSekundy());
        this.rektascensja = r;

        if(obserwowanaWielkoscGwiazdowa < -26.74)
        {
            System.out.println("Wartość obserwowanaWielkoscGwiazdowa mniejsza niż najmniejsza dopuszczalna wartość. Ustawianie najmniejszej dopuszczalnej wartości");
            this.obserwowanaWielkoscGwiazdowa = -26.74;
        }
        else if(obserwowanaWielkoscGwiazdowa > 15.00)
        {
            System.out.println("Wartość obserwowanaWielkoscGwiazdowa większa niż największa dopuszczalna wartość. Ustawianie największej dopuszczalnej wartości");
            this.obserwowanaWielkoscGwiazdowa = 15;
        }
        else
        {
            this.obserwowanaWielkoscGwiazdowa = obserwowanaWielkoscGwiazdowa;
        }

        this.absolutnaWielkoscGwiazdowa = obserwowanaWielkoscGwiazdowa - 5*Math.log10(odleglosc/3.26) + 5;
        this.odleglosc = odleglosc;
        this.gwiazdozbior = gwiazdozbior;
        
        if(d.getStopnie() < 0)
        {
            polkula = "PD";
        }
        else if(d.getStopnie() >= 0)
        {
            polkula = "PN";
        }

        if(temperatura < 2000)
        {
            System.out.println("Wartość temperatura mniejsza niż najmniejsza dopuszczalna wartość. Ustawianie najmniejszej dopuszczalnej wartości");
            this.temperatura = 2000;
        }
        else
        {
            this.temperatura = temperatura;
        }
        
        if(masa > 50.0)
        {
            System.out.println("Wartość masa większa niż największa dopuszczalna wartość. Ustawianie największej dopuszczalnej wartości");
            this.masa = 50.0;
        }
        else if(masa < 0.1)
        {
            System.out.println("Wartość masa mniejsza niż najmniejsza dopuszczalna wartość. Ustawianie najmniejszej dopuszczalnej wartości");
            this.masa = 0.1;
        }
        else
        {
            this.masa = masa;
        }
    }

    //wyświetlanie informacji na temat gwiazdy
    public void WyswietlGwiazde()
    {
        System.out.println(String.format("Nazwa: %s",nazwa));
        System.out.println(String.format("Nazwa katalogowa: %s",nazwaKatalogowa));
        deklinacja.Wyswietl();
        System.out.print("Rektascensja: ");
        System.out.print(String.format("%s godzin ",rektascensja.getHour()));
        System.out.print(String.format("%s minut ",rektascensja.getMinute()));
        System.out.println(String.format("%s sekund ",rektascensja.getSecond()));
        System.out.println(String.format("Obserwowana wielkość gwiazdowa: %s",obserwowanaWielkoscGwiazdowa));
        System.out.println(String.format("Absolutna wielkość gwiazdowa: %s",absolutnaWielkoscGwiazdowa));
        System.out.println(String.format("Odległość: %s",odleglosc));
        System.out.println(String.format("Gwiazdozbiór: %s",gwiazdozbior));
        System.out.println(String.format("Półkula: %s",polkula));
        System.out.println(String.format("Temperatura: %s",temperatura));
        System.out.println(String.format("Masa: %s",masa));
    }
}