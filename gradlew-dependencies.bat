SET MODULE_MAME=%1
call gradlew -q %MODULE_MAME%:dependencies > %MODULE_MAME%_dependencies_report.txt

