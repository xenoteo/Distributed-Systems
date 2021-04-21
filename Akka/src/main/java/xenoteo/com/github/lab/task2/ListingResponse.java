package lab.task2;

import akka.actor.typed.receptionist.Receptionist;

public class ListingResponse implements Command{
    public final Receptionist.Listing listing;

    public ListingResponse(Receptionist.Listing listing){
        this.listing = listing;
    }
}
