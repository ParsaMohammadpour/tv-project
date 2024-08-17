package Model.Request;

import Model.Program.Advertisement.Advertisement;

import java.io.Serializable;

public class Request implements Serializable {
    private Advertisement advertisement;
    private Status status;

    public Request(Advertisement advertisement){
        this.status = Status.PENDING;
        setAdvertisement(advertisement);
    }

    private void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
