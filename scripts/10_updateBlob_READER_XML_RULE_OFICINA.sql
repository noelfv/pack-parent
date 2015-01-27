DECLARE
  TMP_BLOB BLOB := EMPTY_BLOB();
  SRC_CHUNK_01 RAW(32767);
BEGIN
  SRC_CHUNK_01 := UTL_RAW.CAST_TO_RAW('package packws.cargaterritorio

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.bbva.batch.domain.ItemBatch;

rule "100 Valida Oficina"
dialect "java" 
salience 100
no-loop true
when
	items: List() from collect(ItemBatch())
then
	String key;
	ItemBatch io;
	Map<String, String> keys = new HashMap<String, String>();

	for(Object o : items){
		io = (ItemBatch) o;
		io.setObject("__filter__", "0");
		if(io.existsKey("listaOficina.id")) {
		key = io.getString("listaOficina.id");
			if(!keys.containsKey(key)) {
				keys.put(key, key);
				io.setObject("__filter__", "1");
			}
		}
	}
end');

  UPDATE CONELE.MNTR_PARAMETRO SET BINARIO = EMPTY_BLOB() WHERE ID = 10;

  SELECT BINARIO INTO TMP_BLOB
  FROM CONELE.MNTR_PARAMETRO
  WHERE ID = 10 FOR UPDATE;
  
  DBMS_LOB.WRITEAPPEND(TMP_BLOB, UTL_RAW.LENGTH(SRC_CHUNK_01), SRC_CHUNK_01);

  COMMIT;
END;
