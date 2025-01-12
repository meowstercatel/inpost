package Misc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

class Price {
    private float amount;
    private String currency;

    public float getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}

class Parcel {
    private Price price;
    private Dimensions dimensions;

    public Price getPrice() {
        return price;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    static class Dimensions {
        private String dimensionsUnit;
        private String weightUnit;
        private int weight;
        private int width;
        private int height;
        private int length;

        public String getDimensionsUnit() {
            return dimensionsUnit;
        }

        public String getWeightUnit() {
            return weightUnit;
        }

        public int getWeight() {
            return weight;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getLength() {
            return length;
        }
    }
}

class Service {
    private Parcel parcelA;
    private Parcel parcelB;
    private Parcel parcelC;
    private Parcel parcelD;

    public Parcel getParcelA() {
        return parcelA;
    }

    public Parcel getParcelB() {
        return parcelB;
    }

    public Parcel getParcelC() {
        return parcelC;
    }

    public Parcel getParcelD() {
        return parcelD;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Country {
    private String countryCode;
    private Insurance insurance;
    private Service BoxMachineService;
    private Service CourierService;

    public String getCountryCode() {
        return countryCode;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public Service getBoxMachineService() {
        return BoxMachineService;
    }

    public Service getCourierService() {
        return CourierService;
    }

    static class Insurance {
        private Price price;
    }
}

public class ParcelInfo {
    public ExpandAvizo getExpandAvizo() {
        return expandAvizo;
    }

    public Country[] getCountries() {
        return countries;
    }

    static class ExpandAvizo {
        private Price price;
        private int time;

        public Price getPrice() {
            return price;
        }

        public int getTime() {
            return time;
        }
    }
    private ExpandAvizo expandAvizo;
    private Country[] countries;
}
