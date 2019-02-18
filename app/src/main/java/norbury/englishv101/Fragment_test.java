package norbury.englishv101;


import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Debug;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_test extends Fragment implements View.OnClickListener {


    public Fragment_test() {
        dbhelper=new DatabaseHelper(getActivity());
    }

    DatabaseHelper dbhelper;
    View view;

    Button b1,b2,b3,b4;
    Button baslat;

    ArrayList<String> sorulareng=new ArrayList<>();
    ArrayList<String> cevaplartr=new ArrayList<>();
    ArrayList<String> idler=new ArrayList<>();

    List<Integer> random3Sayi = new ArrayList<Integer> ();

    List<Integer> TrueAnswerListe = new ArrayList<Integer> ();
    List<Integer> TotalAnswerListe = new ArrayList<Integer> ();


    TextView soruvetotal;
    TextView sorutv;
    TextView timer;

    public int soru;

    public int counter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.fragment_test, container, false);
        dbhelper = new DatabaseHelper(getActivity());

        soruvetotal=(TextView)view.findViewById(R.id.soruvetotal);

        b1=(Button)view.findViewById(R.id.b1);
        b2=(Button)view.findViewById(R.id.b2);
        b3=(Button)view.findViewById(R.id.b3);
        b4=(Button)view.findViewById(R.id.b4);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);


        Chronometer simpleChronometer = (Chronometer) view.findViewById(R.id.chronometerTime);
        simpleChronometer.setBase(SystemClock.elapsedRealtime());
        simpleChronometer.start();
        simpleChronometer.setFormat("Time - %s");



        sorutv=(TextView)view.findViewById(R.id.tvQuestion);

        soru=0;
        ViewData();
        yenisoru(soru);

        return view;
    }


    public void yenisoru(int soru){

        random3numberGenerator();
        Random rd=new Random();

        soruvetotal.setText((soru+1)+" / "+sorulareng.size());


            if(soru==sorulareng.size())
            {
                Toast.makeText(getActivity(), "Tebrikler Bitti", Toast.LENGTH_LONG).show();
                return;
            }



            sorutv.setText(sorulareng.get(soru));

            int n=rd.nextInt(4);

            switch (n) {
                case 0:

                    DogruSecenek =  1;
                    b1.setText(cevaplartr.get(soru));
                    b2.setText(cevaplartr.get(random3Sayi.get(0)));
                    b3.setText(cevaplartr.get(random3Sayi.get(1)));
                    b4.setText(cevaplartr.get(random3Sayi.get(2)));
                    break;

                case 1:

                    DogruSecenek =  2;
                    b1.setText(cevaplartr.get(random3Sayi.get(0)));
                    b2.setText(cevaplartr.get(soru));
                    b3.setText(cevaplartr.get(random3Sayi.get(1)));
                    b4.setText(cevaplartr.get(random3Sayi.get(2)));
                    break;

                case 2:

                    DogruSecenek =  3;
                    b1.setText(cevaplartr.get(random3Sayi.get(0)));
                    b2.setText(cevaplartr.get(random3Sayi.get(1)));
                    b3.setText(cevaplartr.get(soru));
                    b4.setText(cevaplartr.get(random3Sayi.get(2)));
                    break;

                case 3:

                    DogruSecenek =  4;
                    b1.setText(cevaplartr.get(random3Sayi.get(0)));
                    b2.setText(cevaplartr.get(random3Sayi.get(1)));
                    b3.setText(cevaplartr.get(random3Sayi.get(2)));
                    b4.setText(cevaplartr.get(soru));
                    break;

                default:
                    break;
            }


        }







    public void ViewData(){

        sorulareng.clear();
        cevaplartr.clear();
        idler.clear();
        TrueAnswerListe.clear();
        TotalAnswerListe.clear();

        Cursor data = dbhelper.ShowDataPercent();

        if (data.getCount() == 0) {
            Toast.makeText(getActivity(), "Liste Boş!", Toast.LENGTH_LONG).show();
            return;
        }


        while (data.moveToNext()) {
            idler.add(data.getString(0));
            sorulareng.add(data.getString(1));
            cevaplartr.add(data.getString(2));
            TrueAnswerListe.add(data.getInt(5));
            TotalAnswerListe.add(data.getInt(6));
        }

    }


    public void random3numberGenerator(){
        Random rd=new Random();
        random3Sayi.clear ();
        int falseNumber;
        while (random3Sayi.size() != 3) {
            falseNumber =  rd.nextInt(cevaplartr.size());
            if ((!random3Sayi.contains (falseNumber))&(falseNumber!=soru)){
                random3Sayi.add (falseNumber);
            }

        }

    }






    public void DoğruCevap(int sorusayısı){

        String idmiz=idler.get(sorusayısı);
        int trueanswer=TrueAnswerListe.get(sorusayısı);
        int totalanser=TotalAnswerListe.get(sorusayısı);

        trueanswer++;
        totalanser++;

        boolean UpdateDataValues=dbhelper.updateDataValues(idmiz,trueanswer,totalanser);
        if (UpdateDataValues == true) {
            Toast.makeText(getActivity(), "Değerler"+" ta"+trueanswer+"tt "+totalanser, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Değerler girilirken hata oldu :(.", Toast.LENGTH_LONG).show();
        }

    }

    public void YanlısCevap(int sorusayısı){

        String idmiz=idler.get(sorusayısı);
        int trueanswer=TrueAnswerListe.get(sorusayısı);
        int totalanser=TotalAnswerListe.get(sorusayısı);

        totalanser++;
        boolean UpdateDataValues=dbhelper.updateDataValues(idmiz,trueanswer,totalanser);
        if (UpdateDataValues == true) {
            Toast.makeText(getActivity(), "Değerler girildi!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Değerler girilirken hata oldu :(.", Toast.LENGTH_LONG).show();
        }

    }


    int DogruSecenek;
    @Override
    public void onClick(View v)
    {



        if(v == b1 && DogruSecenek == 1 || v == b2 && DogruSecenek == 2 || v == b3 && DogruSecenek == 3 || v == b4 && DogruSecenek == 4 )
        {

            Toast.makeText(getActivity(), "Cevap Dogru", Toast.LENGTH_SHORT).show();
            DoğruCevap(soru);
            soru++;

            yenisoru(soru);
        }
        else if (v == b2 ||v == b3 || v == b4)
        {

            Toast.makeText(getActivity(), "Cevap Yanlis", Toast.LENGTH_SHORT).show();
            YanlısCevap(soru);
            soru++;
            yenisoru(soru);
        }



    }
}
