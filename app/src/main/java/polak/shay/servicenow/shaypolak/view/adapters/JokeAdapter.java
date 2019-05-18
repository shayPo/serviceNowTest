package polak.shay.servicenow.shaypolak.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import polak.shay.servicenow.shaypolak.R;
import polak.shay.servicenow.shaypolak.model.Joke;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.JokeHolder>
{
    private List<Joke> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    public JokeAdapter()
    {

    }


    @NonNull
    @Override
    public JokeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(mInflater == null)
        {
            mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View v = mInflater.inflate(R.layout.joke_item, viewGroup, false);
        return new JokeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JokeHolder jokeHolder, int position) {
        if(mData != null && mData.size() > position) {
            jokeHolder.setup(mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void addAllJoke(Joke[] jokes) {
        if(jokes == null) return;

        mData.addAll(Arrays.asList(jokes));
        notifyDataSetChanged();
    }

    public void addJoke(Joke add)
    {
        if(add == null) return;
        mData.add(add);
        notifyDataSetChanged();
    }

    public class JokeHolder extends RecyclerView.ViewHolder
    {

        private TextView mText;

        public JokeHolder(@NonNull View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.text);
        }

        public void setup(Joke joke)
        {
                mText.setTag(joke.getId());
                mText.setText(joke.getJoke());
        }
    }
}
