package katana.customerwalkin.feature.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import katana.customerwalkin.R;
import katana.customerwalkin.api.model.BookingData;
import katana.customerwalkin.utils.widget.ExRecyclerView;

/**
 * ka
 * 26/09/2017
 */

public class ServiceAdapter extends ExRecyclerView.Adapter<ServiceAdapter.ServiceHolder> {

    private Context context;
    private List<BookingData> bookingDataList;
    private ExRecyclerView.OnItemClickListener onItemClickListener;

    private int selectedPosition;

    public ServiceAdapter(Context context, List<BookingData> bookingDataList) {
        this.context = context;
        this.bookingDataList = bookingDataList;
        this.selectedPosition = -1;
    }

    public void setOnItemClickListener(ExRecyclerView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ServiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_service, parent, false);
        return new ServiceHolder(v);
    }

    @Override
    public void onBindViewHolder(final ServiceHolder holder, final int position) {
        final BookingData bookingData = bookingDataList.get(position);

        holder.tvName.setText(bookingData.getServiceName());
        holder.tvPrice.setText(bookingData.getServicePrice());

        holder.tvPrice.setTextColor(Color.parseColor(bookingData.getServiceFont()));
        holder.tvName.setTextColor(Color.parseColor(bookingData.getServiceFont()));

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = holder.getAdapterPosition();
                onItemClickListener.onItemClick(view, selectedPosition);
                notifyDataSetChanged();
            }
        });
        if (selectedPosition == position) {
//            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.blue1));
            holder.rootView.setBackgroundColor(ContextCompat.getColor(context, R.color.blue1));
        } else {
//            holder.cardView.setBackgroundColor(Color.parseColor(bookingData.getServiceBg()));
            holder.rootView.setBackgroundColor(Color.parseColor(bookingData.getServiceBg()));
        }
    }

    @Override
    public int getItemCount() {
        return bookingDataList != null ? bookingDataList.size() : 0;
    }

    public static class ServiceHolder extends ExRecyclerView.ViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        //        @BindView(R.id.cardView)
//        CardView cardView;
        @BindView(R.id.rootView)
        RelativeLayout rootView;

        public ServiceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
