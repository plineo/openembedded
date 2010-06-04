DESCRIPTION = "Additional plugins for Enigma2"
MAINTAINER = "Felix Domke <tmbinc@elitedvb.net>"

SRCDATE = "20100119"

PACKAGES_DYNAMIC = "enigma2-plugin-*"

# if you want experimental, use:
REL_MAJOR="2"
REL_MINOR="6"
TAG = ""

# if you want a 2.5-based release, use
#REL_MAJOR="2"
#REL_MINOR="5"
#TAG = ";tag=${PN}_rel${REL_MAJOR}${REL_MINOR}"

PV = "${REL_MAJOR}.${REL_MINOR}cvs${SRCDATE}"

SRC_URI = "cvs://anonymous@cvs.schwerkraft.elitedvb.net/cvsroot/enigma2-plugins;module=enigma2-plugins;method=pserver${TAG};date=${SRCDATE} \
	   file://enigma2-plugins_vuplus.patch;patch=1;pnum=0 \
           file://dreamboxweb.png \
           file://favicon.ico \
	   file://block_install_vuplus.patch;patch=1;pnum=0" 


FILES_${PN} += " /usr/share/enigma2 /usr/share/fonts "
FILES_${PN}-meta = "${datadir}/meta"
PACKAGES += "${PN}-meta"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit autotools

S = "${WORKDIR}/enigma2-plugins"

DEPENDS = "${@get_version_depends(bb, d)}"
DEPENDS += "enigma2"

def get_version_depends(bb, d):
	if bb.data.getVar('REL_MINOR', d, 1) > '4':
		return "python-pyopenssl"
	return ""

do_install_append() {
	install -m 0644 ${WORKDIR}/dreamboxweb.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/favicon.ico ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
}


python populate_packages_prepend () {
	enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)

	do_split_packages(d, enigma2_plugindir, '(.*?/.*?)/.*', 'enigma2-plugin-%s', 'Enigma2 Plugin: %s', recursive=True, match_path=True, prepend=True)

	def getControlLines(mydir, d, package):
		import os
		try:
			src = open(mydir + package + "/CONTROL/control").read()
		except IOError:
			return
		for line in src.split("\n"):
			if line.startswith('Package: '):
				full_package = line[9:]
			if line.startswith('Depends: '):
				bb.data.setVar('RDEPENDS_' + full_package, ' '.join(line[9:].split(', ')), d)
			if line.startswith('Description: '):
				bb.data.setVar('DESCRIPTION_' + full_package, line[13:], d)

	mydir = bb.data.getVar('D', d, 1) + "/../enigma2-plugins/"
	for package in bb.data.getVar('PACKAGES', d, 1).split():
		getControlLines(mydir, d, package.split('-')[-1])
}

