package spryrocks.com.tristimer.presentation.ui.screens.results;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import spryrocks.com.tristimer.R;
import spryrocks.com.tristimer.data.Result;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {
    @Nullable
    private List<Result> results;

    ResultsAdapter(@Nullable List<Result> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public ResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.results_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsAdapter.ViewHolder holder, int position) {
        if (results == null)
            throw new RuntimeException("results should not be null");

        Result result = results.get(position);
        holder.time.setText(result.getTime());
        holder.scramble.setText(result.getScramble());
        holder.date.setText(result.getDate());
    }

    @Override
    public int getItemCount() {
        if (results == null)
            return 0;

        return results.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView scramble;
        TextView date;
        ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            scramble = itemView.findViewById(R.id.scramble);
            date = itemView.findViewById(R.id.date);
        }
    }
}

