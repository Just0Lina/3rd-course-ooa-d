#run from dir with files!
sed -i 's/package io.swagger.model;/package literavibe.model.dto;/' ./*.java
sed -i '/import io.swagger.v3.oas*/d' ./*.java
sed -i '/@Schema*/d' ./*.java
sed -i 's/import io.swagger.model./import literavibe.model.dto./' ./*.java
sed -i "s/javax/jakarta/g" ./*.java
