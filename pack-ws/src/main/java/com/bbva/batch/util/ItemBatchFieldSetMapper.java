package com.bbva.batch.util;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import com.bbva.batch.domain.ItemBatch;
import com.bbva.batch.enums.DataType;

public class ItemBatchFieldSetMapper extends BeanWrapperFieldSetMapper<ItemBatch> {

    private String className[];

    public String[] getClassName() {
        return className;
    }

    public void setClassName(String[] className) {
        this.className = className;
    }

    public ItemBatch mapFieldSet(FieldSet fs) {
        ItemBatch item =new ItemBatch();
        DataType type;
        String colName;
        
        for(int i = 0; i < fs.getFieldCount(); i++) {
            colName = fs.getNames()[i];
            type = DataType.valueOf(className[i].toUpperCase());
            
            if(DataType.STRING.compareTo(type) == 0) {
                item.setObject(colName, fs.readString(i));
            } else if(DataType.INTEGER.compareTo(type) == 0) {
                item.setObject(colName, fs.readInt(i));
            } else if(DataType.LONG.compareTo(type) == 0) {
                item.setObject(colName, fs.readLong(i));
            } else if(DataType.DATE.compareTo(type) == 0) {
                item.setObject(colName, fs.readDate(i));
            } else if(DataType.DOUBLE.compareTo(type) == 0) {
                item.setObject(colName, fs.readDouble(i));
            } else if(DataType.BIGDECIMAL.compareTo(type) == 0) {
                item.setObject(colName, fs.readBigDecimal(i));
            }
        }
        
        return item;
    }
}
