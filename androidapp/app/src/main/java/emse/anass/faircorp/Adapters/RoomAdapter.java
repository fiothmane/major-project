package emse.anass.faircorp.Adapters;

import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import emse.anass.faircorp.ContextManagementActivity;
import emse.anass.faircorp.R;
import emse.anass.faircorp.models.Room;
import emse.anass.faircorp.models.Status;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ItemViewHolder> {


    private List<Room> itemsData;
    private ContextManagementActivity activity;


    public RoomAdapter(ContextManagementActivity activity, List<Room> itemsData) {
        this.itemsData = itemsData;
        this.activity = activity;
    }


    @Override
    public RoomAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        return new ItemViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Room item = itemsData.get(position);
        holder.nameTxt.setText(item.getName());
        if(item.getLights() != null){
            if(item.getLights().get(0).getStatus() == Status.ON){
                holder.imageView.setImageResource(R.drawable.ic_bulb_on);
            }else{
                holder.imageView.setImageResource(R.drawable.ic_bulb_off);
            }
        }else{
            holder.imageView.setImageResource(R.drawable.ic_bulb_off);
        }

    }


    @Override
    public int getItemCount() {
        return itemsData.size();
    }


    public void removeAllItems() {
        itemsData.clear();
        notifyDataSetChanged();
    }


    public Room getSelectedItem(int position) {
        return itemsData.get(position);
    }

    public void addItem(Room item) {
        itemsData.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(int indice){
        itemsData.remove(indice);
        notifyItemRemoved(indice);
    }

    public void addAllItems(List<Room> rooms){
        itemsData.addAll(rooms);
        notifyDataSetChanged();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameTxt;
        public ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nameTxt = itemView.findViewById(R.id.room_name);
            imageView = itemView.findViewById(R.id.iv_room);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            Room room = getSelectedItem(position);

            Log.i("Room", "" + room.getName());
            highlight(view);
        }

        private void highlight(final View view) {
            view.setBackgroundColor(ContextCompat.getColor(activity, R.color.selected_item));
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                }
            }, 100);
        }
    }
}
