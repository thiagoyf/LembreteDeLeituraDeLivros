package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thiagoyf.lembretedeleituradelivros.R;

import java.util.List;

import model.Livro;
import util.ImagemUtil;

/**
 * Created by thiagoyf on 12/12/16.
 */

public class LivroAdapter extends BaseAdapter {

    private Context context;
    private List<Livro> livros;

    public LivroAdapter(Context context, List<Livro> livros) {
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
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Livro livro = livros.get(i);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.livro, null);
        }

        ImageView imagem = (ImageView) view.findViewById(R.id.livro_imagem);
        if (livro.getFoto() != null) {
            imagem.setImageBitmap(ImagemUtil.byteToBitmap(livro.getFoto()));
        } else {
            imagem.setImageResource(R.drawable.no_image_available);
        }

        TextView txtNome = (TextView) view.findViewById(R.id.livro_nome);
        String titulo = context.getString(R.string.listarLivros_txtTitulo) + livro.getNome();
        txtNome.setText(titulo);

        TextView txtTotalPaginas = (TextView) view.findViewById(R.id.livro_totalPaginas);
        String totalPaginas = context.getString(R.string.listarLivros_txtTotalPaginas) + livro
                .getTotalPaginas();
        txtTotalPaginas.setText(totalPaginas);

        return view;
    }
}
