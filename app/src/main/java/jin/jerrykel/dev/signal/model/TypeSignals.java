package jin.jerrykel.dev.signal.model;

/**
 * Created by JerrykelDEV on 08/01/2021 10:09
 */
public class TypeSignals {
    private String name;
    private String urlImage;

    public TypeSignals(String name, String urlImage) {
        this.name = name;
        this.urlImage = urlImage;
    }
    public TypeSignals(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
