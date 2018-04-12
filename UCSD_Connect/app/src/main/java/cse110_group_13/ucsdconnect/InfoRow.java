package cse110_group_13.ucsdconnect;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * Description: This class creates a reusable component to display a "row" of information with a
 * label and a body.
 *
 * For example:
 *     EVENT TITLE
 *     Some Event Title
 *
 *     EVENT DESCRIPTION
 *     Some event description goes here!
 *
 * Public Methods: InfoRow (Context context)
 *                 InfoRow (Context context, AttributeSet attrs)
 *                 InfoRow (Context context, AttributeSet attrs, int defStyle)
 *                 init (Context context, AttributeSet attrs)
 *                 setTitleText (String text)
 *                 setContentText (String text)
 */

public class InfoRow extends LinearLayout {
    private TextView title;
    private TextView content;

    public InfoRow (Context context) {
        super(context);
        init(context, null);
    }

    public InfoRow (Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InfoRow (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init (Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.info_row, this);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);

        title = (TextView) findViewById(R.id.row_title);
        content = (TextView) findViewById(R.id.row_content);

        if (attrs != null) {
            final TypedArray xs = context.getTheme().obtainStyledAttributes(attrs, R.styleable.InfoRow, 0, 0);
            try {
                if (xs.hasValue(R.styleable.InfoRow_titleText)) {
                    setTitleText(xs.getString(R.styleable.InfoRow_titleText));
                }
                if (xs.hasValue(R.styleable.InfoRow_contentText)) {
                    setContentText(xs.getString(R.styleable.InfoRow_contentText));
                }
            } finally {
                xs.recycle();
            }
        }
    }

    public void setTitleText (String text) {
        title.setText(text);
    }

    public void setContentText (String text) {
        content.setText(text);
    }
}
