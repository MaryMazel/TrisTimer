package spryrocks.com.tristimer.presentation.ui.screens.results;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import spryrocks.com.tristimer.R;
import spryrocks.com.tristimer.data.Result;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {
    private List<ResultItem> resultItems;

    ResultsAdapter() {
        this.resultItems = new ArrayList<>();
    }

    void setItems(@NonNull List<ResultItem> items) {
        resultItems.clear();
        resultItems.addAll(items);
        notifyDataSetChanged();
    }

    List<ResultItem> getItems() {
        return new ArrayList<>(resultItems);
    }

    @NonNull
    @Override
    public ResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.results_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsAdapter.ViewHolder holder, int position) {
        if (resultItems == null)
            throw new RuntimeException("results should not be null");

        ResultItem resultItem = resultItems.get(position);

        holder.resultItem = resultItem;

        holder.time.setText(resultItem.result.getTime());
        holder.scramble.setText(resultItem.result.getScramble());
        holder.date.setText(resultItem.result.getDate());
        holder.view.setBackgroundColor(resultItem.isSelected() ? Color.parseColor("#6501579B"): Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        if (resultItems == null)
            return 0;

        return resultItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        ResultItem resultItem;

        View view;
        TextView time;
        TextView scramble;
        TextView date;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            time = itemView.findViewById(R.id.time);
            scramble = itemView.findViewById(R.id.scramble);
            date = itemView.findViewById(R.id.date);

            view.setOnLongClickListener(v -> {
                if (resultItem == null)
                    return true;

                resultItem.setSelected(!resultItem.isSelected());
                notifyDataSetChanged();
                return true;
            });
        }
    }

    public static class ResultItem {
        boolean selected;
        final Result result;

        ResultItem(Result result) {
            this.result = result;
            this.selected = false;
        }

        void setSelected(boolean selected) {
            this.selected = selected;
        }

        boolean isSelected() {
            return selected;
        }
    }
}

