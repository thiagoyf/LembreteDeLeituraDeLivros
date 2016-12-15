package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thiagoyf.lembretedeleituradelivros.R;

import java.util.List;

import model.Lembrete;
import model.Livro;
import util.DataUtil;

/**
 * Created by thiagoyf on 12/15/16.
 */

public class LembreteAdapter extends BaseAdapter{

    private Context context;
    private List<Lembrete> lembretes;
    private List<Livro> livros;

    public LembreteAdapter(Context context, List<Livro> livros, List<Lembrete> lembretes) {
        this.context = context;
        this.livros = livros;
        this.lembretes = lembretes;
    }

    @Override
    public int getCount() {
        return lembretes.size();
    }

    @Override
    public Object getItem(int i) {
        return lembretes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Lembrete lembrete = lembretes.get(i);
        Livro livro = livros.get(i);

        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.lembrete, null);
        }

        TextView txtData = (TextView) view.findViewById(R.id.lembrete_data);
        String data = context.getString(R.string.listarLembretes_txtData) + DataUtil.formatDbPt(lembrete.getDatahora())[0];
        txtData.setText(data);

        TextView txtHora = (TextView) view.findViewById(R.id.lembrete_hora);
        String hora = context.getString(R.string.listarLembretes_txtHora) + DataUtil.formatDbPt(lembrete.getDatahora())[1];
        txtHora.setText(hora);

        TextView txtLivro = (TextView) view.findViewById(R.id.lembrete_livro);
        String titulo = context.getString(R.string.listarLembretes_txtLivro) + livro.getNome();
        txtLivro.setText(titulo);

        return view;
    }
}
