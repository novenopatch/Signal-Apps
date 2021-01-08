package jin.jerrykel.dev.signal.vue.Activities.Main.fragment.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by JerrykelDEV on 06/01/2021 16:25
 */
public class SpinerAdapter extends ArrayAdapter<String> {


    public SpinerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }


}
