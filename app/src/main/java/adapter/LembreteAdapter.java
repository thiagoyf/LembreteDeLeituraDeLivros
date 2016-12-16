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

public class LembreteAdapter extends BaseAdapter {

    private Context context;
    private List<Lembrete> lembretes;

    public LembreteAdapter(Context context, List<Lembrete> lembretes) {
        this.context = context;
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

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.lembrete, null);
        }

        TextView txtData = (TextView) view.findViewById(R.id.lembrete_data);
        String data = context.getString(R.string.listarLembretes_txtData) + DataUtil
                .formatDataDbPt(lembrete.getDatahora());
        txtData.setText(data);

        TextView txtHora = (TextView) view.findViewById(R.id.lembrete_hora);
        String hora = context.getString(R.string.listarLembretes_txtHora) + DataUtil
                .formatHoraDbPt(lembrete.getDatahora());
        txtHora.setText(hora);

        TextView txtLivro = (TextView) view.findViewById(R.id.lembrete_livro);
        String titulo = context.getString(R.string.listarLembretes_txtLivro) + lembrete.getLivro
                ().getNome();
        txtLivro.setText(titulo);

        TextView txtRepete = (TextView) view.findViewById(R.id.lembrete_repete);
        String repete = context.getString(R.string.listarLembretes_txtRepetir) + lembrete
                .getRepeteDia();
        txtRepete.setText(repete);

        return view;
    }
}
