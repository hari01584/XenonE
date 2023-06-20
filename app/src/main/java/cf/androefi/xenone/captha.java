package cf.androefi.xenone;

import android.content.Context;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class captha {
    private Context context;

    public captha(Context context) {
        this.context = context;
    }

    public void getCaptha(final ImageView imageView) {
        Picasso.get()
                .load("https://route.sandboxol.com/user/api/v1/vc/cimage?uid=" + Util.userId + "&type=0")
                .resize(500, 250)
                .into(imageView);
    }
}
