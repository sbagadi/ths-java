package ths.core.security;

public class IPLocation {
	private byte[] country;
	private byte[] area;
	
	public byte[] getCountry() {
		return country;
	}
	
	public void setCountry(byte[] country) {
		this.country = country;
	}
	
	public byte[] getArea() {
		return area;
	}
	
	public void setArea(byte[] area) {
		this.area = area;
	}
	
	public String getCountryAsString()
	{
		String s = "unknown";
		
		if (country != null) {
			try {
				s = new String(country, "UTF-8");
				if (s.trim().equals("CZ88.NET")) {
					s = "unknown";
					area = null;
				}				
			} catch (Exception e) {
			}
		}

		return s;
	}
	
	public String getAreaAsString()
	{
		String s = "unknown";
		
		if (area != null) {
			try {
				s = new String(area, "UTF-8");
				if (s.trim().equals("CZ88.NET")) {
					s = "unknown";
				}
			} catch (Exception e) {
			}
		}

		return s;
	}
}
