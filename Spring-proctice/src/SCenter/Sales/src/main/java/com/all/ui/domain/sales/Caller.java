package com.AL.ui.domain.sales;

import java.util.ArrayList;
import java.util.List;
/*import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;*/

public class Caller {

   private String externalId;

   private String firstName;

   private String middleName;

   private String lastName;

   private String creditScore;
    
   private Dwelling primaryDwelling;

   private Address address;
    
   private Address correctedAddress;

   private List<Address>candidateAddress;

   public String getExternalId() {
      return externalId;
   }

   public void setExternalId(String externalId) {
      this.externalId = externalId;
   }

   public Address getCorrectedAddress() {
      return correctedAddress;
   }
   
   public void setCorrectedAddress(Address arg) {
      correctedAddress = arg;
   }

	public String getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(String creditScore) {
		this.creditScore = creditScore;
	}

	public Dwelling getPrimaryDwelling() {
		return primaryDwelling;
	}

	public void setPrimaryDwelling(Dwelling primaryDwelling) {
		this.primaryDwelling = primaryDwelling;
	}
	
	public boolean hasDwelling() {
		return (null != getPrimaryDwelling());
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	public boolean equals(Caller c) {
      if (c == null) {
         return false;   
      }

     //boolean retval = ObjectUtils.equals(getLastName(), c.getLastName());
      boolean retval = false;
     /* if (retval) {
         retval = ObjectUtils.equals(getFirstName(), c.getFirstName());
         if (retval) {
            retval = ObjectUtils.equals(getAddress(), c.getAddress());
         }
      }*/
      return retval;
	}

   public Object getName() 
   {
      return this;
   }

   public String getFirst() 
   {
      return firstName;
   }

   public String getLast() 
   {
      return lastName;
   }

   public Dwelling getDwelling() {
      return getPrimaryDwelling();
   }

   public String getAddressString() {
      String retval = "";

      if (address != null) {
         String arg = address.getStreet();
        /* if (StringUtils.isBlank(arg) == false) {
            retval = arg;
         }

         arg = address.getStreet2();
         if (StringUtils.isBlank(arg) == false) {
            retval = retval + ", " + arg;
         }

         arg = address.getCity();
         if (StringUtils.isBlank(arg) == false) {
            retval = retval + ", " + arg;
         }

         arg = address.getState();
         if (StringUtils.isBlank(arg) == false) {
            retval = retval + ", " + arg;
         }

         arg = address.getZip();
         if (StringUtils.isBlank(arg) == false) {
            retval = retval + ", " + arg;
         }*/

         return retval;
      }
      
      return retval;
   }

    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getMiddleName() {
        return this.middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public Address getAddress() {
        return this.address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }

    public void addCandidateAddress(Address address) {
       getCandidateAddress().add(address);
    }

   public List<Address> getCandidateAddress() {
      if (candidateAddress == null) {
         candidateAddress = new ArrayList<Address>();
      }
      return candidateAddress;
   }

   public void setCandidateAddress(List<Address> candidateAddress) {
      this.candidateAddress = candidateAddress;
   } 
}
