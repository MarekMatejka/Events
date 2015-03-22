package mm.events.views;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class LogoRadioButton extends RadioButton {

    public LogoRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setText(Html.fromHtml("&#x"+getText().toString()));

        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "FontAwesome.otf"));

        this.setFocusable(true);
    }
}