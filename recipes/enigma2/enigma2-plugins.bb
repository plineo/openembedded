DESCRIPTION = "Additional plugins for Enigma2"
MAINTAINER = "Felix Domke <tmbinc@elitedvb.net>"

PACKAGES_DYNAMIC = "enigma2-plugin-*"

# if you want experimental, use:
SRCREV=""
SRCDATE="20101217"
BRANCH="master"
PV = "experimental-git${SRCDATE}"

PR = "r0"

SRCDATE_bm750 = "20101112"
SRCDATE_vusolo = "20101112"

SRC_URI="git://schwerkraft.elitedvb.net/enigma2-plugins/enigma2-plugins.git;protocol=git;branch=${BRANCH};tag=${SRCREV}"

EXTRA_OECONF = " \
        BUILD_SYS=${BUILD_SYS} \
        HOST_SYS=${HOST_SYS} \
        STAGING_INCDIR=${STAGING_INCDIR} \
        STAGING_LIBDIR=${STAGING_LIBDIR} \
"

SRC_URI_vuplus = "cvs://anonymous@cvs.schwerkraft.elitedvb.net/cvsroot/enigma2-plugins;module=enigma2-plugins;method=pserver;date=${SRCDATE} \
	   file://enigma2_plugins_mytube_tpm.patch;patch=1;pnum=1 \
#	   file://enigma2_plugins_webinterface_tpm.patch;patch=1;pnum=1 \
	   file://enigma2_plugins_ac3lipsync_dolby.patch;patch=1;pnum=1 \
           file://dreamboxweb.png \
           file://favicon.ico"

FILES_${PN} += " /usr/share/enigma2 /usr/share/fonts "
FILES_${PN}-meta = "${datadir}/meta"
PACKAGES += "${PN}-meta"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit autotools

S = "${WORKDIR}/enigma2-plugins"

DEPENDS = "python-pyopenssl python-gdata streamripper python-mutagen python-daap"
DEPENDS += "enigma2"


def modify_po():
	import os
	try:
		os.system("find ./ -name \"*.po\" > ./po_list")
		os.system("find ./ -name \"*.pot\" >> ./po_list")
		po_list = []
		po_list = open('po_list','r+').readlines()
		for x in po_list:
			changeword1(x)
		changeword1('enigma2-plugins/networkwizard/src/networkwizard.xml ')
		changeword2('enigma2-plugins/webinterface/src/web-data/tpl/default/index.html ')
		os.system('rm po_list')
	except:
		print 'word patch error '
		return

def changeword1(file):
	fn = file[:-1]
	fnn = file[:-1]+'_n'
	cmd = "sed s/Dreambox/STB/g "+fn+" > "+fnn
	os.system(cmd)
	cmd1 = "mv "+fnn+" "+fn
	os.system(cmd1)

def changeword2(file):
	fn = file[:-1]
	fnn = file[:-1]+'_n'
	cmd = "sed s/Dreambox/Vu+/g "+fn+" > "+fnn
	os.system(cmd)
	cmd1 = "mv "+fnn+" "+fn
	os.system(cmd1)

do_unpack_append(){
	modify_po()
}


do_install_append_vuplus() {
	install -m 0644 ${WORKDIR}/dreamboxweb.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/favicon.ico ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
}

python populate_packages_prepend () {
	enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)

	do_split_packages(d, enigma2_plugindir, '(.*?/.*?)/.*', 'enigma2-plugin-%s', 'Enigma2 Plugin: %s', recursive=True, match_path=True, prepend=True)

	def getControlLines(mydir, d, package):
		import os
		try:
			#ac3lipsync is renamed since 20091121 to audiosync.. but rename in cvs is not possible without lost of revision history..
			#so the foldername is still ac3lipsync
			if package == 'audiosync':
				package = 'ac3lipsync'
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
			if line.startswith('Replaces: '):
				bb.data.setVar('RREPLACES_' + full_package, ' '.join(line[10:].split(', ')), d)
			if line.startswith('Conflicts: '):
				bb.data.setVar('RCONFLICTS_' + full_package, ' '.join(line[11:].split(', ')), d)
			if line.startswith('Maintainer: '):
				bb.data.setVar('MAINTAINER_' + full_package, line[12:], d)

	mydir = bb.data.getVar('D', d, 1) + "/../git/"
	for package in bb.data.getVar('PACKAGES', d, 1).split():
		getControlLines(mydir, d, package.split('-')[-1])
}
