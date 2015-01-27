public class StringToHex {
    
    public static String stringToHex(String base) {
        StringBuffer buffer = new StringBuffer();
        int intValue;
        for (int x = 0; x < base.length(); x++) {
            int cursor = 0;
            intValue = base.charAt(x);
            String binaryChar = new String(Integer.toBinaryString(base.charAt(x)));
            for (int i = 0; i < binaryChar.length(); i++) {
                if (binaryChar.charAt(i) == '1') {
                    cursor += 1;
                }
            }
            if ((cursor % 2) > 0) {
                intValue += 128;
            }
            buffer.append(Integer.toHexString(intValue) + "");
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        String s = "{\"class\":\"com.everis.webservice.WSDLResource\",\"elements\":{\"obtenerTerritorioRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"Oficina\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreOficina\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"territorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinaMadre\",\"ref\":null,\"type\":\"OficinaMadre\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinasHijas\",\"ref\":null,\"type\":\"OficinaHija\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"Oficina\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaTerritorioRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"obtenerOficinaResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"listarAreaResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaArea\",\"ref\":null,\"type\":\"Suprarea\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"listarTerritorioRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codArea\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"listarTerritorioResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaTerritorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaTerritorioSuprareaRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":\"%\"}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"listarAreaRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codBanca\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"OficinaMadre\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreOficina\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"OficinaMadre\",\"ref\":null,\"type\":null,\"value\":null},\"Territorio\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"suprarea\",\"ref\":null,\"type\":\"Suprarea\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinas\",\"ref\":null,\"type\":\"OficinaTerritorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"Territorio\",\"ref\":null,\"type\":null,\"value\":null},\"OficinaHija\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreOficina\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinaMadre\",\"ref\":null,\"type\":\"OficinaMadre\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"OficinaHija\",\"ref\":null,\"type\":null,\"value\":null},\"Banca\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreSuprarea\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"Banca\",\"ref\":null,\"type\":null,\"value\":null},\"obtenerTerritorioResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"territorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"Suprarea\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreSuprarea\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"suprarea\",\"ref\":null,\"type\":\"Banca\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"Suprarea\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"obtenerOficinaRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codOficina\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaTerritorioSuprareaResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaTerritorioResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"OficinaTerritorio\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreOficina\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinaMadre\",\"ref\":null,\"type\":\"OficinaMadre\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"idTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinasHijas\",\"ref\":null,\"type\":\"OficinaHija\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"OficinaTerritorio\",\"ref\":null,\"type\":null,\"value\":null}},\"messages\":{\"obtenerTerritorio\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerTerritorio\",\"part\":null},\"obtenerOficinaResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerOficinaResponse\",\"part\":null},\"listarAreaResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaArea\",\"ref\":null,\"type\":\"Suprarea\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarAreaResponse\",\"part\":null},\"listarTerritorioResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaTerritorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarTerritorioResponse\",\"part\":null},\"listarArea\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codBanca\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarArea\",\"part\":null},\"obtenerOficina\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codOficina\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerOficina\",\"part\":null},\"obtenerTerritorioResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"territorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerTerritorioResponse\",\"part\":null},\"listarOficinaResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaResponse\",\"part\":null},\"listarOficinaTerritorioSuprareaResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"part\":null},\"listarTerritorio\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codArea\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarTerritorio\",\"part\":null},\"listarOficinaTerritorio\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorio\",\"part\":null},\"listarOficina\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficina\",\"part\":null},\"listarOficinaTerritorioSuprarea\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":\"%\"}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioSuprarea\",\"part\":null},\"listarOficinaTerritorioResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioResponse\",\"part\":null}},\"operations\":{\"obtenerTerritorio\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerTerritorio\",\"part\":null},\"name\":\"obtenerTerritorio\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"territorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerTerritorioResponse\",\"part\":null}},\"obtenerOficina\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codOficina\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerOficina\",\"part\":null},\"name\":\"obtenerOficina\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerOficinaResponse\",\"part\":null}},\"listarTerritorio\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codArea\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarTerritorio\",\"part\":null},\"name\":\"listarTerritorio\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaTerritorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarTerritorioResponse\",\"part\":null}},\"listarOficinaTerritorio\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorio\",\"part\":null},\"name\":\"listarOficinaTerritorio\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioResponse\",\"part\":null}},\"listarOficina\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficina\",\"part\":null},\"name\":\"listarOficina\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaResponse\",\"part\":null}},\"listarOficinaTerritorioSuprarea\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":\"%\"}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioSuprarea\",\"part\":null},\"name\":\"listarOficinaTerritorioSuprarea\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"part\":null}},\"listarArea\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codBanca\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarArea\",\"part\":null},\"name\":\"listarArea\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaArea\",\"ref\":null,\"type\":\"Suprarea\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarAreaResponse\",\"part\":null}}},\"targetNamespace\":\"http://www.bbva.com.pe/CentrosBBVAWebService/\",\"url\":\"http://192.168.247.1:8081/CentrosBBVAWS/CentrosBBVAWebService?wsdl\"}";
        String st;
        
        for(int i = 0; i < s.length(); i++) {
            st = s.substring(i, (i + 1000 + 1) >= s.length() ? s.length() - 1 : (i + 1000 + 1) );
            // System.out.println(st);
            System.out.println(StringToHex.stringToHex(st));
            i += 1000;
        }
        // System.out.println(s);
        // System.out.println(StringToHex.stringToHex());
        // System.out.println(StringToHex.stringToHex(s));
    }

}
