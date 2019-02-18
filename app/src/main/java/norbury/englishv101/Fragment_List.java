package norbury.englishv101;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static norbury.englishv101.DatabaseHelper.TABLE_NAME;


/**
 * A simple {@link Fragment} subclass.
 */




public class Fragment_List extends Fragment {


    /*
    * Bu kısım Db Bağlantıları, Listview a db den verileri çekmek ve eklemek için gerekli.
    * */

    DatabaseHelper dbhelper;
    View view;
    ListView liste;


    ArrayList<String> veri=new ArrayList<>();
    final Context context = getActivity();
    ArrayAdapter<String> adapter;
    EditText ueng,utr,upro,ucom;



    public Fragment_List() {
        dbhelper=new DatabaseHelper(getActivity());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment__list, container, false);
        liste = (ListView) view.findViewById(R.id.display_list);
        dbhelper = new DatabaseHelper(getActivity());

         adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,veri);//

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                //Toast.makeText(getActivity(), veri.get(position) + " seçtiniz" +id+"İd", Toast.LENGTH_LONG).show();
                final Dialog dialog = new Dialog(getActivity(),R.style.Theme_AppCompat_Dialog_Alert);
                dialog.setContentView(R.layout.popup);





                Button dialogGun = (Button) dialog.findViewById(R.id.btnGun);
                Button dialogSil=(Button) dialog.findViewById(R.id.btnSil);


                //Veriler Girilecek//****************************************

                TextView etEng=(TextView) dialog.findViewById(R.id.kelimeEng);
                TextView etTr=(TextView) dialog.findViewById(R.id.kelimeTr);
                TextView etPro=(TextView) dialog.findViewById(R.id.pro);
                TextView etCom=(TextView) dialog.findViewById(R.id.Comment);
                TextView etProUp=(TextView) dialog.findViewById(R.id.Per);

                etEng.setText(idileEngCek(position));
                etTr.setText(idileTrCek(position));
                etPro.setText(idileProCek(position));
                etCom.setText(idileComCek(position));
                etProUp.setText(idilePerCek(position));

                dialogGun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  Toast.makeText(getActivity(), "Güncelleme", Toast.LENGTH_LONG).show();


                        final Dialog dialogup = new Dialog(getActivity(),R.style.Theme_AppCompat_Dialog_Alert);
                        dialogup.setContentView(R.layout.uppopup);

                        ueng=(EditText) dialogup.findViewById(R.id.UetdEng);
                        utr=(EditText)dialogup.findViewById(R.id.UetdTr);
                        upro=(EditText)dialogup.findViewById(R.id.UetdPro);
                        ucom=(EditText)dialogup.findViewById(R.id.UetdCom);

                        ueng.setText(idileEngCek(position));
                        utr.setText(idileTrCek(position));
                        upro.setText(idileProCek(position));
                        ucom.setText(idileComCek(position));

                        dialogup.show();

                        Button guncelle=(Button)dialogup.findViewById(R.id.guncelle);

                        guncelle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UpdateData(idileIdcek(position),ueng.getText().toString(),utr.getText().toString(),upro.getText().toString(),ucom.getText().toString());
                                dialogup.hide();

                            }
                        });



                    }
                });

                dialogSil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Sil", Toast.LENGTH_LONG).show();
                        DeleteData(idileIdcek(position));
                       adapter.remove(adapter.getItem(position));
                        liste.invalidateViews();

                        liste.setSelection(position-1);
                        dialog.hide();

                    }
                });

                dialog.show();

            }
        });


        liste.setAdapter(adapter);

        ViewData();
        return view;
    }


    public  String idileIdcek(int pos){
        Cursor data = dbhelper.showData();
        String temp;
        data.moveToPosition(pos);
        temp=data.getString(0);
        return temp;
    }


    //Burada textview da posion vr ya id leri saklayıp pozisyon üzerinden tüm bilgiyi almak için yazdım. bu data olayıda her sferinde tekrar tanımlamaya gerek var mı ki

    public  String idileEngCek(int pos){
        Cursor data = dbhelper.showData();
        String temp;
        data.moveToPosition(pos);
        temp=data.getString(1);
        return temp;
    }
    public  String idileTrCek(int pos){
        Cursor data = dbhelper.showData();
        String temp;
        data.moveToPosition(pos);
        temp=data.getString(2);
        return temp;
    }

    public  String idileComCek(int pos){
        Cursor data = dbhelper.showData();
        String temp;
        data.moveToPosition(pos);
        temp=data.getString(4);
        return temp;
    }
    public  String idileProCek(int pos){
        Cursor data = dbhelper.showData();
        String temp;
        data.moveToPosition(pos);
        temp=data.getString(3);
        return temp;
    }

    public  String idilePerCek(int pos){
        Cursor data = dbhelper.showData();
        String temp;
        data.moveToPosition(pos);

        temp="Doğru: " +data.getInt(5) + " Toplam: " +data.getInt(6)+" Percent: "+data.getInt(7);
        return temp;
    }


    public void ViewData(){

                Cursor data = dbhelper.showData();

                if (data.getCount() == 0) {
                    Toast.makeText(getActivity(), "Liste Boş!", Toast.LENGTH_LONG).show();
                    return;
                }

                String temp;

                while (data.moveToNext()) {
                     temp=data.getString(1) + " "+ data.getString(2)+" "+data.getInt(7);
                     veri.add(temp);
                }

            }

    public void DeleteData(String kelimeId){

                int temp = kelimeId.toString().length();


                if(temp > 0){
                    Integer deleteRow = dbhelper.deleteData(kelimeId);
                    if(deleteRow > 0){
                        Toast.makeText(getActivity(), "Silme İşlemi başarılı! ", Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(getActivity(), "HATA! Silme başarısız. :(.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "HATA! Bir şeyler ters gitti. :(.", Toast.LENGTH_LONG).show();
                }

    }


    public void UpdateData(String kelimeid,String Eng, String Tr, String Pro, String Com){

                int temp = kelimeid.toString().length();
                if (temp > 0) {
                    boolean update = dbhelper.updateData(kelimeid,Eng, Tr, Pro, Com);
                    if (update == true) {
                        Toast.makeText(getActivity(), "Veri Güncelleme Başarılı!", Toast.LENGTH_LONG).show();


                    } else {
                        Toast.makeText(getActivity(), "HATA! Güncelleme başarısız. :(.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "HATA! Bir şeyler ters gitti. :(.", Toast.LENGTH_LONG).show();
                }

    }








}
