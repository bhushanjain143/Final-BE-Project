package com.technohub.remind;


public class Product {
    private	int	id;
    private	String name;
    private	String quantity;
    private float latlong;
    private float longitude;

    private	String priority;


    public Product(String name, String quantity,float latlong,float longitude,String priority) {
        this.setName(name);
        this.setQuantity(quantity);
        this.setLatlong(latlong);
           this.setLongitude(longitude);
           this.setPriority(priority);


    }

    public Product(int id, String name, String quantity,float latlong,float longitude,String priority) {
        this.setId(id);
        this.setName(name);
        this.setQuantity(quantity);
        this.setLatlong(latlong);
        this.setLongitude(longitude);
       // this.setLongitude(longitude);
        this.setPriority(priority);
    }



    public int getId()
    {

        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()

    {
        return name;
    }



    public String getQuantity()

    {
        return quantity;
    }



    public float getLatlong()
    {
        return latlong;
    }



    public String getPriority()
    {
        return priority;
    }



    public float getLongitude()
    {
        return longitude;
    }
    public void setName(String name)

    {
        this.name = name;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }

    public void setQuantity(String quantity)

    {
        this.quantity = quantity;
    }

    public void setLatlong(float latlong) {
        this.latlong = latlong;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
