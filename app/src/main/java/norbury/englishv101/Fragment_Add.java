package norbury.englishv101;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Add extends Fragment  {

    DatabaseHelper dbhelper;
    Button btnAdd;
    View view;
    EditText etEng,etTr,etCom,etPro;

    public Fragment_Add() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment__add, container, false);
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        dbhelper = new DatabaseHelper(getActivity());
            AddData();



        etEng=(EditText)view.findViewById(R.id.etdEng);
        etTr=(EditText)view.findViewById(R.id.etdTr);
        etPro=(EditText)view.findViewById(R.id.etdPro);
        etCom=(EditText)view.findViewById(R.id.etdCom);
        return view;


    }
    //Ekleme fragmenti bu klasik işte edittextlerden veriyi çekip yolluyorum dbhelper la gerekli fonksiyna dbdeki karakter sınırına göre kontrol konabilir belki çökmelere karşı varchar diyorsun varchar50 yapıyoruz meselasa 51 karakter denenirse hata çıkar onun yerine hata mesajı çıkartıp eklenemez dese iyi olur
    // Kk bir de onu soracaktım sana şimdi yazıyorum çalışıyor da ben uygulamayı try catchler ile sarıp sarmalasam mı diye düşünüyorum. çünkü db uygulaması her tel de sıkıntı olabilir, çökerse kötü duruyor. adamın saygısı kalmıyor.
    // Try catchleri nasıl yapayım, bi önerin var mı ? knk her yere doldurma bi tek ilk bağlantı sağladığın yere koy yeter
    // Db ile bağlantı mı yokssa db kurulumu vs mi? program ilk açıldığında işte dbye bağlanıyor ya o kısım kurulumda zaten olsun ama ekle çıkar güncelle yada okurken gerek yok
    public void AddData(){

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Eng=etEng.getText().toString();
                String Tr=etTr.getText().toString();
                String Com=etCom.getText().toString();
                String Pro=etPro.getText().toString();

                boolean insertData = dbhelper.addData(Eng, Tr, Pro, Com);
               if (insertData == true) {
                    Toast.makeText(getActivity(), "Başarıyla Eklendi!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Ekleme sırasında hata :(.", Toast.LENGTH_LONG).show();
                }
            }


        });
    }

}
