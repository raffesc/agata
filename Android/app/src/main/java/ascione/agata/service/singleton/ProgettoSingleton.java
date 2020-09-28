package ascione.agata.service.singleton;

import java.util.ArrayList;
import java.util.List;

import ascione.agata.service.network.response.BugsRecordResponse;
import ascione.agata.service.network.response.ProgettiRecordResponse;
import ascione.agata.service.network.response.UserListRecordResponse;
import ascione.agata.service.network.response.UserResponse;

public class ProgettoSingleton {

    private ProgettiRecordResponse progetto;

    private List<BugsRecordResponse> bugs;

    private String title;

    private String descrizione;

    private Boolean isPrivate;

    private static final ProgettoSingleton ourInstance = new ProgettoSingleton();

    public static ProgettoSingleton getInstance() { return ourInstance; }

    private ProgettoSingleton() {}

    public ProgettiRecordResponse getProgetto() {
        return progetto;
    }

    public void setProgetto(ProgettiRecordResponse progetto) {
        this.progetto = progetto;
    }

    public List<BugsRecordResponse> getBugs() {
        return bugs;
    }

    public void setBugs(List<BugsRecordResponse> bugs) {
        this.bugs = bugs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
