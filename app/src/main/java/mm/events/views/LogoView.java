package mm.events.views;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

public class LogoView extends TextView {

    public LogoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setText(Html.fromHtml("&#x"+getText().toString()));

        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"FontAwesome.otf"));

        this.setClickable(true);
        this.setFocusable(true);
    }

    public void setLogo(String code){
        this.setText(Html.fromHtml("&#x"+code));
    }

    public void setEmpty() {
        this.setText("");
    }

    @Override
    public boolean isInEditMode() { return true; }
}