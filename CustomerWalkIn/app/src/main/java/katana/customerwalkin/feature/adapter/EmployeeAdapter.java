package katana.customerwalkin.feature.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import katana.customerwalkin.R;
import katana.customerwalkin.api.model.Employee;
import katana.customerwalkin.utils.ImageLoaderUtils;
import katana.customerwalkin.utils.widget.ExRecyclerView;

/**
 * ka
 * 26/09/2017
 */

public class EmployeeAdapter extends ExRecyclerView.Adapter<EmployeeAdapter.EmployeeHolder> {

    private static final int TYPE = 0;
    private static final int TYPE_NORMAL = 1;
    private Context context;
    private List<Employee> employees;
    private ExRecyclerView.OnItemClickListener onItemClickListener;
    private int selectedPosition;


    public EmployeeAdapter(Context context, List<Employee> employees) {
        this.context = context;
        this.employees = employees;
        this.selectedPosition = -1;
    }

    public void setOnItemClickListener(ExRecyclerView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        notifyDataSetChanged();
    }

    @Override
    public EmployeeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = viewType == TYPE ? R.layout.item_skip : R.layout.item_employee;
        View v = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new EmployeeHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE : TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(final EmployeeHolder holder, final int position) {
        if (getItemViewType(position) == TYPE) {
            holder.ivAvatar.setVisibility(View.GONE);
            holder.tvSkip.setVisibility(View.VISIBLE);
        } else {
            final Employee e = employees.get(position - 1);

            holder.tvSkip.setVisibility(View.GONE);
            holder.tvName.setVisibility(View.VISIBLE);
            holder.tvName.setText(e.getFullName());
            if (!e.getPicture().isEmpty()) {
                ImageLoaderUtils.show(context, e.getPicture(), holder.ivAvatar);
            }

            assert holder.cardView != null;
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedPosition = holder.getAdapterPosition() - 1;
                    onItemClickListener.onItemClick(view, selectedPosition);
                    notifyDataSetChanged();

                }
            });
            if (selectedPosition == position - 1) {
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.blue1));
            } else {
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
            }
        }
    }

    @Override
    public int getItemCount() {
        return employees.size() + 1;
    }

    public static class EmployeeHolder extends ExRecyclerView.ViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvSkip)
        TextView tvSkip;
        @BindView(R.id.ivAvatar)
        ImageView ivAvatar;
        @Nullable
        @BindView(R.id.cardView)
        CardView cardView;

        public EmployeeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
