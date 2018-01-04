package managedbeans;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

public class RestaurantValueListener implements ValueChangeListener{

	@Override
	public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
		RegistrationTransferBean rtransfer = (RegistrationTransferBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("registrationTransferBean");	
		
		rtransfer.setName_restaurant_rating(event.getNewValue().toString());
		
		rtransfer.restaurantChanged(event.getNewValue().toString());
	}

}
