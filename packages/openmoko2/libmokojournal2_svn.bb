SECTION = "openmoko/libs"
DEPENDS = "eds-dbus"
PV = "0.1.0+svn${SVNREV}"
PR = "r1"

inherit openmoko2

do_stage() {
        autotools_stage_all
}