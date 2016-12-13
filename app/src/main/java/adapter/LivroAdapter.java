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

import model.Livro;

/**
 * Created by thiagoyf on 12/12/16.
 */

public class LivroAdapter extends BaseAdapter {

    private Context context;
    private List<Livro> livros;

    public LivroAdapter (Context context, List<Livro> livros){
        this.context = context;
        this.livros = livros;
    }

    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int i) {
        return livros.get(i);
    }

    @Override
    public long getItemId(int i) {
        return livros.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Livro livro = livros.get(i);

        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.livro, null);
        }

        TextView nome = (TextView) view.findViewById(R.id.livro_nome);
        nome.setText("Título do Livro: " + livro.getNome());

        TextView totalPaginas = (TextView) view.findViewById(R.id.livro_totalPaginas);
        totalPaginas.setText("Total de Páginas: " + livro.getTotalPaginas());

        return view;
    }
}
