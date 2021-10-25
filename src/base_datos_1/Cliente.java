/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base_datos_1;

import java.io.IOException;

/**
 *
 * @author alramirez
 */
public class Cliente {    
    
    public Integer id;
    public String code;
    public int male;
    public int type;
    public String location;
    public String company;
    public int encrypt;
    
    public Cliente(Integer id, String code, int male, int type, String location, String company, int encrypt) {
        this.id = id;
        this.code = code;
        this.male = male;
        this.type = type;
        this.location = location;
        this.company = company;
        this.encrypt = encrypt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() throws IOException {
        if(this.encrypt == 1){
            NetClientGet connNetClientGet = new NetClientGet("https://test.evalartapp.com/extapiquest/code_decrypt/", this.code);
            String codigo = connNetClientGet.getCode();
            code = codigo;
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMale() {
        return male;
    }

    public void setMale(int male) {
        this.male = male;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(int encrypt) {
        this.encrypt = encrypt;
    }
}
