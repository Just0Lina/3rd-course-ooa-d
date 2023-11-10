#run from dir with files!
sed -i 's/package io.swagger.model;/package voicebookserver.security.dto;/' ./*.java
sed -i '/import io.swagger.v3.oas*/d' ./*.java
sed -i '/@Schema*/d' ./*.java
sed -i 's/import io.swagger.model./import voicebookserver.security.dto./' ./*.java