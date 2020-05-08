// Custom package mapping
["java:package:cl.ucn.disc.pdis.lab.zeroice"]
module model
{
    // The API
    interface Engine
    {
        string getDate();

        string getDigitoVerificador(string rut);
    }

}
