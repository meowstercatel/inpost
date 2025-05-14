import Authentication.Reauthentication;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

class PhoneNumber {
    private String value;
    private String prefix;

    public String getValue() {
        return value;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public String toString() {
        return "Phone number: " + this.getPrefix() + " " + this.getValue() + "\n";
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Operations {
    private boolean manualArchive;
    private String autoArchivableSince;
    private boolean delete;
    private boolean collect;
    private boolean expandAvizo;
    private boolean highlight;
    private String refreshUntil;
    private String requestEasyAccessZone;
    private boolean voicebot;
    private boolean canShareToObserve;
    private boolean canShareOpenCode;
    private boolean canShareParcel;

    public boolean isManualArchive() {
        return manualArchive;
    }

    public String getAutoArchivableSince() {
        return autoArchivableSince;
    }

    public boolean isDelete() {
        return delete;
    }

    public boolean isCollect() {
        return collect;
    }

    public boolean isExpandAvizo() {
        return expandAvizo;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public String getRefreshUntil() {
        return refreshUntil;
    }

    public String getRequestEasyAccessZone() {
        return requestEasyAccessZone;
    }

    public boolean isVoicebot() {
        return voicebot;
    }

    public boolean isCanShareToObserve() {
        return canShareToObserve;
    }

    public boolean isCanShareOpenCode() {
        return canShareOpenCode;
    }

    public boolean isCanShareParcel() {
        return canShareParcel;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class PickUpPoint {
    private String name;
    private Location location;
    private String locationDescription;
    private String openingHours;
    private AddressDetails addressDetails;
    private int virtual;
    private String pointType;
    private String[] type;
    private boolean location247;
    private boolean doubled;
    private String imageUrl;
    private boolean easyAccessZone;
    private boolean airSensor;
    private String country;

    @Override
    public String toString() {
        return "Pickup Point" + "\n" +
                "\t name: " + this.getName() + "\n" +
                "\t country: " + this.getCountry() + "\n" +
                "\t location description: " + this.getLocationDescription() + "\n" +
                "\t is 24/7: " + this.isLocation247() + "\n";
    }

    static class Location {
        private double latitude;
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

    static class AddressDetails {
        private String postCode;
        private String city;
        private String province;
        private String street;
        private int buildingNumber;
        private String country;

        public String getPostCode() {
            return postCode;
        }

        public String getCity() {
            return city;
        }

        public String getProvince() {
            return province;
        }

        public String getStreet() {
            return street;
        }

        public int getBuildingNumber() {
            return buildingNumber;
        }

        public String getCountry() {
            return country;
        }
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public AddressDetails getAddressDetails() {
        return addressDetails;
    }

    public int getVirtual() {
        return virtual;
    }

    public String getPointType() {
        return pointType;
    }

    public String[] getType() {
        return type;
    }

    public boolean isLocation247() {
        return location247;
    }

    public boolean isDoubled() {
        return doubled;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isEasyAccessZone() {
        return easyAccessZone;
    }

    public boolean isAirSensor() {
        return airSensor;
    }

    public String getCountry() {
        return country;
    }
}

class LogEvent {
    private String type;
    private String name;
    private String date;

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
class Event {
    private String eventDescription;
    private String eventTitle;
    private String date;

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getDate() {
        return date;
    }
}

class Receiver {
    private String email;
    private String name;
    private PhoneNumber phoneNumber;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Receiver\n" +
                "\tEmail: " + this.getEmail() + "\n" +
                "\tName: " + this.getName() + "\n" +
                "\t" + this.getPhoneNumber();
    }
}

class Sender {
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Sender\n" +
                "\tname: " + name + '\n';
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ForeignParcel {
    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    private String shipmentNumber;
    private String shipmentType;
    private int openCode;
    private String qrCode;
    private String storedDate;
    private String pickUpDate;
    private PickUpPoint pickUpPoint;
    private boolean endOfWeekCollection;
    private Operations operations;
    private String status;
    private String statusGroup;
    private LogEvent[] eventLog;
    private String avizoTransactionStatus;
    private String ownershipStatus;
    private String economyParcel;
    private String internationalParcel;
    private String parcelSize;
    private Receiver receiver;
    private Sender sender;
    private Event[] events;
    private String[] sharedTo;

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public int getOpenCode() {
        return openCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getStoredDate() {
        return storedDate;
    }

    public String getPickUpDate() {
        return pickUpDate;
    }

    public PickUpPoint getPickUpPoint() {
        return pickUpPoint;
    }

    public boolean isEndOfWeekCollection() {
        return endOfWeekCollection;
    }

    public Operations getOperations() {
        return operations;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusGroup() {
        return statusGroup;
    }

    public LogEvent[] getEventLog() {
        return eventLog;
    }

    public Event[] getEvents() {
        return events;
    }

    public String getAvizoTransactionStatus() {
        return avizoTransactionStatus;
    }

    public String getOwnershipStatus() {
        return ownershipStatus;
    }

    public String getEconomyParcel() {
        return economyParcel;
    }

    public String getInternationalParcel() {
        return internationalParcel;
    }

    public String getParcelSize() {
        return parcelSize;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public Sender getSender() {
        return sender;
    }

    public String[] getSharedTo() {
        return sharedTo;
    }

    @Override
    public String toString() {
        return "%s|%s|%s|%s".formatted(this.shipmentNumber, this.status,  this.getPickUpPoint().getName(), this.getReceiver().getEmail());
    }
}

class PersonalParcel extends ForeignParcel {
    public Byte[] generateQrCode() {
        String qrCode = super.getQrCode();
        //dummy implementation
        return new Byte[qrCode.length()];
    }
    public void openLocker(Inpost inpost) {
        String data = "{\"accessToken\": \"%s\",\"openCode\": \"%s\"}".formatted(inpost.getAccessToken(), super.getOpenCode());

        InpostRequest inpostRequest = Inpost.request("https://api-inmobile-pl.easypack24.net/v1/OpenParcel", data);
    }

    @Override
    public String toString() {
        return "%s|%s|%s|%s".formatted(super.getShipmentNumber(), super.getStatus(),  this.getPickUpPoint().getName(), this.getReceiver().getEmail());
    }
}