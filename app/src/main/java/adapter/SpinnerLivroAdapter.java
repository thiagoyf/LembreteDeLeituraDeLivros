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

/**
 * Created by thiagoyf on 12/13/16.
 */

public class SpinnerLivroAdapter extends BaseAdapter {

    private Context context;
    private List<String> nomeLivros;

    public SpinnerLivroAdapter(Context context, List<String> nomeLivros){
        this.context = context;
        this.nomeLivros = nomeLivros;
    }

    @Override
    public int getCount() {
        return nomeLivros.size();
    }

    @Override
    public Object getItem(int i) {
        return nomeLivros.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String nomeLivro = nomeLivros.get(i);

        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.spinner_livro, null);
        }

        TextView nome = (TextView) view.findViewById(R.id.spinner_livro_nome);
        nome.setText(nomeLivro);

        return view;
    }
}
