
package tikape.tikaperyhmatyo.domain;

public class Ingredient {
    private Integer id;
    private String name;
    private Integer howMany;
    
    public Ingredient(Integer id, String name, Integer howMany) {
        this.id = id;
        this.name = name;
        this.howMany = howMany;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHowMany() {
        return howMany;
    }

    public void setHowMany(Integer howMany) {
        this.howMany = howMany;
    }
    
}
