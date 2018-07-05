package akil.co.tz.notetaker.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import akil.co.tz.notetaker.R;
import akil.co.tz.notetaker.models.Notification;

/**
 * Created by DevDept on 6/14/18.
 */

public class NotificationAdapter
        extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final List<Notification> mValues;
//    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Post item = (Post) view.getTag();
//            Context context = view.getContext();
//            Intent intent;
//            if(item.getDetails() != null)
//                intent = new Intent(context, NoteDetailActivity.class);
//            else
//                intent = new Intent(context, NoteEditActivity.class);
//
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("post", item);
//            intent.putExtras(bundle);
//
//            context.startActivity(intent);
//        }
//    };

    public NotificationAdapter(List<Notification> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Notification notification = mValues.get(position);
        String title = notification.getTitle();
        Boolean has_title = title != null;

        if(has_title)
            holder.mTitleView.setText(title);

        if(notification.getMessage() != null)
            holder.mMessageView.setText(notification.getMessage());

        if(position == getItemCount() - 1)
            holder.mSeparatorView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mValues == null ? 0 : mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mTitleView;
        final TextView mMessageView;
        final View mSeparatorView;

        ViewHolder(View view) {
            super(view);
            mTitleView = view.findViewById(R.id.title_view);
            mMessageView = view.findViewById(R.id.message_view);
            mSeparatorView = view.findViewById(R.id.separator_view);
        }
    }
}