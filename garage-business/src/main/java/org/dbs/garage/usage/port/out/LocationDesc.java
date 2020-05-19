package org.dbs.garage.usage.port.out;

public class LocationDesc implements Comparable<LocationDesc> {
    private final String locationName;
    private final String adressName;

    public LocationDesc(String locationName, String adressName) {
        this.locationName = locationName;
        this.adressName = adressName;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getAdressName() {
        return adressName;
    }

    @Override
    public String toString() {
        return "LocationDesc{" +
                "locationName='" + locationName + '\'' +
                ", adressName='" + adressName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationDesc)) return false;

        LocationDesc that = (LocationDesc) o;

        if (!getLocationName().equals(that.getLocationName())) return false;
        return getAdressName().equals(that.getAdressName());
    }

    @Override
    public int hashCode() {
        int result = getLocationName().hashCode();
        result = 31 * result + getAdressName().hashCode();
        return result;
    }

    @Override
    public int compareTo(LocationDesc otherLocationDesc) {
        int result = this.getLocationName().compareTo(otherLocationDesc.getLocationName());
        if (result == 0) {
            result = this.getAdressName().compareTo(otherLocationDesc.getAdressName());
        }
        return result;
    }
}
