package cse110_group_13.ucsdconnect;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/*
 * Class Notes: Based on boilerplate code auto-generated by Android Studio.
 *
 * Description: RecyclerViewAdapter for the categories list.
 *
 * Public Methods: MyCategoryRecyclerViewAdapter(List<Category> category,
 *                         CategoryFragment.OnCategoryListFragmentInteractionListener listener)
 *                 onCreateViewHolder(ViewGroup parent, int viewType)
 *                 onBindViewHolder(final ViewHolder holder, int position)
 *                 getItemCount()
 *
 */

public class MyCategoryRecyclerViewAdapter extends RecyclerView.Adapter<MyCategoryRecyclerViewAdapter.ViewHolder> {


    private final List<Category> mCateogry;
    private final CategoryFragment.OnCategoryListFragmentInteractionListener mListener;
    public static boolean fromCategory = false;
    public static String currentCategory = null;

    public MyCategoryRecyclerViewAdapter(List<Category> category, CategoryFragment.OnCategoryListFragmentInteractionListener listener) {
        mCateogry = category;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setCategory(mCateogry.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onCategoryInfoRequested(holder.mCategory);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCateogry.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView mCategoryName;
        public final TextView mCategoryDetail;

        private Category mCategory;

        public ViewHolder(View view) {

            super(view);
            Log.d("VIEWHOLDER", "Creating View");
            mView = view;
            mCategoryName = (TextView) view.findViewById(R.id.category_name);
            mCategoryDetail = (TextView) view.findViewById(R.id.category_description);
        }

        public void setCategory (Category category) {

            Log.d("VIEWHOLDER", "Setting View");

            mCategoryName.setText(category.name);
            mCategoryDetail.setText(category.description);
            mCategory = category;
        }
        @Override
        public String toString() {
            return super.toString() + " '" + mCategoryName.getText() + "'";
        }
    }
}
