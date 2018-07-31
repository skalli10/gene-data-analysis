i=0

ls TabakoffData | sed -e  '/^B.*$/d' -e  '/^Affy_HAPLAP_MOE430v2$/d' -e 's/^/TabakoffData\//g' | sort > folders.txt
echo TabakoffData/BxD_Affy_Chip/Affy_BxD_12 >> folders.txt
echo TabakoffData/BxD_Affy_Chip/Affy_BxD_38 >> folders.txt
echo TabakoffData/BxD_Affy_Chip/Affy_BxD_8 >> folders.txt

echo EdenbergData >> folders.txt

echo HitzemannData/Whole*/MOE430A >> folders.txt
echo HitzemannData/Whole*/MOE430B >> folders.txt
echo HitzemannData/Whole*/U74A >> folders.txt
# echo HitzemannData/Whole*/U74B >>  folders.txt
echo HitzemannData/Whole*/U74C >> folders.txt

FOLDERS=$(cat folders.txt)
 for folder in $FOLDERS
  do
  echo $folder
  FILE_NAMES=$(ls $folder | sed -e 's/.CEL.Rdata//g' -e  's/.CEL.xml//g' -e  '/^.*.txt$/d' -e  '/^.*.R$/d' -e  '/^.*.xls$/d' -e  '/^.*.CEL$/d' -e  '/^.*.R~$/d' -e '/^.*.R#$/d' -e  '/^.*.bad$/d' | sort | uniq)
  
    for file in $FILE_NAMES
    do
     (( i = i + 1 ))
        echo CallLoad $i $folder $file
       java -Xmx1073741824 -classpath ".:/stroma/NeoCore/API/Java/lib/neoHTTPConnection.jar" CallLoad  $i $folder $file
    done
done

