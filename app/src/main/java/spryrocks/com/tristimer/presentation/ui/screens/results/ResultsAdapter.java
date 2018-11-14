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

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder> {
    @Nullable
    private List<String> results;

    private final LayoutInflater inflater;

    public ResultsAdapter(@NonNull Context context, @Nullable List<String> results) {
        this.results = results;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ResultsAdapter.ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.results_item, parent, false);

        return new ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsAdapter.ResultsViewHolder holder, int position) {
        if (results == null)
            throw new RuntimeException("results should not be null");

        holder.text.setText(results.get(position));
    }

    @Override
    public int getItemCount() {
        if (results == null)
            return 0;

        return results.size();
    }

    class ResultsViewHolder extends RecyclerView.ViewHolder {
        final TextView text;
        ResultsViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }
}

