package in.catking.gkapp.fragC;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import in.catking.gkapp.R;
import in.catking.gkapp.quiz.QuizList;
import in.catking.gkapp.test1;

public class frag_quiz_ELV_adapter extends BaseExpandableListAdapter {

    private Context context;
    private List<fragC_quiz_model> listDataHeader;
    private HashMap<fragC_quiz_model, List<fragC_quiz_model>> listDataChild;

    public frag_quiz_ELV_adapter(Context context, List<fragC_quiz_model> listDataHeader,
                                 HashMap<fragC_quiz_model, List<fragC_quiz_model>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public fragC_quiz_model getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition).menuName;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_child, null);
        }

        TextView txtListChild = convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if (this.listDataChild.get(this.listDataHeader.get(groupPosition)) == null)
            return 0;
        else
            return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                    .size();
    }

    @Override
    public fragC_quiz_model getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
//        String headerTitle = getGroup(groupPosition).menuName;
//        if (convertView == null) {
//            LayoutInflater infalInflater = (LayoutInflater) this.context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = infalInflater.inflate(R.layout.quiz_item_newitem, null);
//        }
//
//        TextView lblListHeader = convertView.findViewById(R.id.quiz);
//        lblListHeader.setText(headerTitle);
//
//        return convertView;
        frag_quiz_ViewHolder holder = null;
        if (convertView == null) {
            holder = new frag_quiz_ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.quiz_item_newitem, parent, false);
            holder.quiz = (TextView) convertView.findViewById(R.id.quiz);

            convertView.setTag(holder);
        } else {
            holder = (frag_quiz_ViewHolder) convertView.getTag();
        }
        holder.quiz.setId(groupPosition);
        String headerTitle = getGroup(groupPosition).menuName;
        try{
            holder.quiz.setText(headerTitle);

        }catch(Exception e) {}
        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

class frag_quiz_ViewHolder {
    TextView quiz;
}

