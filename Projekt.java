import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Projekt {

    //tablica liter greckich
    public static String[] tablicaLiter = { "alfa ", "beta ", "gamma ", "delta ", "epsilon ", "zeta ", "eta ", "theta ",
            "iota ", "kappa ", "lambda ", "my " ,"ny ","xy ","omicron ","pi ","rho ","sigma ","tau ", "ipsilon ","phi ","chi ","psi ","omega "};

    //metoda zapisująca obiekt do bazy
    public static void ZapiszDoBazy(Gwiazda obiekt, String plik) 
    {
        File p = new File(plik);

        ObjectOutputStream strumien_zapis = null;
        Gwiazda[] tablicaGwiazd = OdczytDoTablicy(plik);

        try
        {
            strumien_zapis = new ObjectOutputStream(new FileOutputStream(plik));

            //sprawdzanie czy plik istnieje - jeśli nie, ustawia nazwę katologową pierwszego rekordu w bazie i zapisuję rekord do bazy
            if(!p.exists())
            {
                obiekt.setNazwaKatalogowa(tablicaLiter[0].concat(obiekt.getGwiazdozbior()));

                strumien_zapis.writeObject(obiekt);
            }
            //dodawanie następnego rekordu do bazy
            //istniejące rekordy są wczytywane do tablicy
            else
            {
                int licznik = 0;
                for(int i = 0 ; i < tablicaGwiazd.length ; i++)
                {
                    //liczenie wystąpień gwiazdozbioru w tablicy
                    if(obiekt.getGwiazdozbior().equals(tablicaGwiazd[i].getGwiazdozbior()))
                    {
                        licznik++;
                    }
                }
                //ponowne zapisywanie elementów
                for (Gwiazda g : tablicaGwiazd) {
                    strumien_zapis.writeObject(g);
                }
                //ustawienie wartości nazwy katalogowej nowego elementu i zapisanie go do pliku
                obiekt.setNazwaKatalogowa(tablicaLiter[licznik].concat(obiekt.getGwiazdozbior()));
                strumien_zapis.writeObject(obiekt);
            }

            strumien_zapis.close();
        }
        catch(FileNotFoundException e)
        {
            
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }

    //metoda wyświetla wszystkie rekordy w bazie na ekranie
    public static void Odczyt(String plik) 
    {
        Gwiazda[] tablicaGwiazd = OdczytDoTablicy(plik);

        for (Gwiazda g : tablicaGwiazd) 
        {
            g.WyswietlGwiazde();
            System.out.println("-----------------------");
        }
    }

    //metoda czyta plik z bazą i zwraca wszystkie rekordy w postaci tablicy typu Gwiazda
    public static Gwiazda[] OdczytDoTablicy(String plik)
    {
        ObjectInputStream strumien_odczyt = null;
        List<Gwiazda> listaGwiazd = new ArrayList<Gwiazda>();

        try
        {
            strumien_odczyt = new ObjectInputStream(new FileInputStream(plik));

            Object obj = null;
            Gwiazda test = null;

            while((obj = strumien_odczyt.readObject())!=null)
            {
                if(obj instanceof Gwiazda)
                {
                    test = (Gwiazda)obj;
                    listaGwiazd.add(test);
                }
            }
            strumien_odczyt.close();
        }
        catch(FileNotFoundException e)
        {
            //e.printStackTrace();
        }
        catch(EOFException e)
        {
            //e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        Gwiazda[] tablicaGwiazd = new Gwiazda[listaGwiazd.size()];
        listaGwiazd.toArray(tablicaGwiazd);

        return tablicaGwiazd;
    }

    //metoda usuwa rekord z bazy
    public static void UsunObiektZBazy(String nazwa_kat, String plik) 
    {
        ObjectOutputStream strumien_zapis = null;
        Gwiazda[] tablicaGwiazd = OdczytDoTablicy(plik);
        boolean znaleziony = false;
        int miejsce = 0;


        //nazwa_kat.substring(nazwa_kat.indexOf(' ')+1).equals(tablicaGwiazd[i].nazwaKatalogowa.substring(tablicaGwiazd[i].nazwaKatalogowa.indexOf(' ')+1
        try
        {
            strumien_zapis = new ObjectOutputStream(new FileOutputStream(plik));

            for(int i = 0 ; i < tablicaGwiazd.length ; i++)
            {
                //sprawdzanie czy dany rekord będzie mógł podlegać zmianom, w przeciwnym wypadku po prostu się go zapisuje
                //jeśli część zmiennej nazwa_kat po wystąpeniu pierwszej spacji jest zgodna z wartością gwiazdozbior, to oznacza że element może podlegać zmianom
                if(nazwa_kat.substring(nazwa_kat.indexOf(' ')+1).equals(tablicaGwiazd[i].getGwiazdozbior()))
                {
                    //sprawdzanie czy rekord który miał zostać usunięty został już znaleziony
                    if(nazwa_kat.equals(tablicaGwiazd[i].getNazwaKatalogowa()))
                    {
                        znaleziony = true;
                    }
                    //jeśli rekord który miał być usunięty został znaleziony, ustawia się odpowiednie nazwy katalogowe dla każdych kolejnych rekordów
                    else if(znaleziony == true)
                    {
                        tablicaGwiazd[i].setNazwaKatalogowa(tablicaLiter[miejsce].concat(tablicaGwiazd[i].getGwiazdozbior()));
                        strumien_zapis.writeObject(tablicaGwiazd[i]);
                        miejsce++;
                    }
                    else
                    {
                        strumien_zapis.writeObject(tablicaGwiazd[i]);
                        miejsce++;
                    }
                }
                else
                {
                    strumien_zapis.writeObject(tablicaGwiazd[i]);
                }
            }

            strumien_zapis.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //wyszukuje wszystkie gwiazdy w gwiazdozbiorze
    public static void WyszukajWGwiazdozbiorze(String zadanyGwiazdozbior, String plik) 
    {
        Gwiazda[] tablicaGwiazd = OdczytDoTablicy(plik);

        for (Gwiazda g : tablicaGwiazd) 
        {
            if(g.getGwiazdozbior().equals(zadanyGwiazdozbior))    
            {
                g.WyswietlGwiazde();
                System.out.println("-----------------------");
            }
        }
    }

    //wyszukuje gwiazdy znajdujace się w zadanej odległości podanej w parsekach
    public static void WyszukajOdleglosc(double zadanaOdleglosc, String plik)
    {
        Gwiazda[] tablicaGwiazd = OdczytDoTablicy(plik);

        for (Gwiazda g : tablicaGwiazd) 
        {
            if(g.getOdleglosc() < zadanaOdleglosc)    
            {
                g.WyswietlGwiazde();
                System.out.println("-----------------------");
            }
        }
    }

    //wyszukuje gwiazdy o temperaturze w zadanym przedziale
    public static void WyszukajTemperatura(int poczatek, int koniec,String plik)
    {
        Gwiazda[] tablicaGwiazd = OdczytDoTablicy(plik);

        for (Gwiazda g : tablicaGwiazd) 
        {
            if((g.getTemperatura() >= poczatek) && (g.getTemperatura() <= koniec))    
            {
                g.WyswietlGwiazde();
                System.out.println("-----------------------");
            }
        }
    }

    //wyszukuje gwiazdy o wielkosci gwiazdowej w zadanym przedziale
    public static void WyszukajWielkoscGwiazdowa(double poczatek, double koniec, String plik)
    {
        Gwiazda[] tablicaGwiazd = OdczytDoTablicy(plik);

        for (Gwiazda g : tablicaGwiazd) 
        {
            if((g.getAbsolutnaWielkoscGwiazdowa() >= poczatek) && (g.getAbsolutnaWielkoscGwiazdowa() <= koniec))    
            {
                g.WyswietlGwiazde();
                System.out.println("-----------------------");
            }
        }
    }

    //wyszukuje gwiazdy na podstawie półkuli
    public static void WyszukajPolkula(String zadanaPolkula,String plik)
    {
        Gwiazda[] tablicaGwiazd = OdczytDoTablicy(plik);

        for (Gwiazda g : tablicaGwiazd) {
            if(g.getPolkula().equals(zadanaPolkula))
            {
                g.WyswietlGwiazde();
                System.out.println("-----------------------");
            }
        }
    }

    //wyszukuje potencjalne supernowe - masa > 1.44 masy słońca
    public static void WyszukajSupernowe(String plik)
    {
        Gwiazda[] tablicaGwiazd = OdczytDoTablicy(plik);

        for (Gwiazda g : tablicaGwiazd) {
            if(g.getMasa() > 1.44)
            {
                g.WyswietlGwiazde();
                System.out.println("-----------------------");
            }
        }   
    }

    public static void main(String[] args) {   
        // Gwiazda G1 = new Gwiazda("ABC 1234", new Deklinacja(60, 24.003, 23.4), LocalTime.of(1,12,23), 10, 293, "wolarz", 12000, 20);
        // Gwiazda G2 = new Gwiazda("ABC 3452", new Deklinacja(54, 12, 53), LocalTime.of(23,24,12), 20, 365, "wolarz", 26000, 20);
        // Gwiazda G3 = new Gwiazda("ABC 4353", new Deklinacja(12, 23, 34), LocalTime.of(12,34,54), 0.2, 345, "wolarz", 2700, 1);
        // Gwiazda G4 = new Gwiazda("EFG 7866", new Deklinacja(-65, 34.03, 45.232), LocalTime.of(14,54,23), -12, 234, "byk", 12000, 1.55);
        // Gwiazda G5 = new Gwiazda("EFG 4564", new Deklinacja(-23, 12.0234, 45.232), LocalTime.of(17,23,34), 15, 234, "byk", 45000, 50);
        // Gwiazda G6 = new Gwiazda("IJH 9890", new Deklinacja(-45, 54.03, 23.123), LocalTime.of(20,54,54), 14, 114, "centaur", 10000000, 25);
        // Gwiazda G7 = new Gwiazda("IJH 1235", new Deklinacja(65, 34.03, 45.232), LocalTime.of(4,40,12), 6, 100, "centaur", 100000, 42);
        // Gwiazda G8 = new Gwiazda("IJH 4543", new Deklinacja(89, 59.03, 54.232), LocalTime.of(12,32,23), -20, 101, "centaur", 2500, 34);
        // Gwiazda G9 = new Gwiazda("IJH 4543", new Deklinacja(89, 59.03, 54.232), LocalTime.of(12,32,23), -20, 101, "centaur", 2500, 34);

        // ZapiszDoBazy(G1, "Baza.obj");
        // ZapiszDoBazy(G2, "Baza.obj");
        // ZapiszDoBazy(G3, "Baza.obj");
        // ZapiszDoBazy(G4, "Baza.obj");
        // ZapiszDoBazy(G5, "Baza.obj");
        // ZapiszDoBazy(G6, "Baza.obj");
        // ZapiszDoBazy(G7, "Baza.obj");
        // ZapiszDoBazy(G8, "Baza.obj");
        
        //UsunObiektZBazy("beta centaur", "Baza.obj");
        //WyszukajWGwiazdozbiorze("centaur", "Baza.obj");
        //WyszukajOdleglosc(1000, "Baza.obj");
        //WyszukajPolkula("PN","Baza.obj");
        //WyszukajSupernowe("Baza.obj");
        //WyszukajTemperatura(2000, 3000, "Baza.obj");

        //Odczyt("Baza.obj");
    }
}
