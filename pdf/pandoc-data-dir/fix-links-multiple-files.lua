-- fix-links-multiple-files.lua
-- https://stackoverflow.com/questions/48169995/pandoc-how-to-link-to-a-section-in-another-markdown-file/48172069#48172069
-- FYI: we convert links
--      so that on local file system it works with MacDown ... with relative pathes
--      so that it works on nextcloud ... with ?fileID=43185
--      so that it works (after conversion, see below) in the pdf and we have absolute pathes
--      TODO: make it work within pdf with anchors/links WITHIN the single PDF file
function Link (link)
  -- for:                                                               ./study-material--overview.md
  -- generate: https://git-iit.fh-joanneum.at/omd/ios-devel/-/tree/master/study-material--overview.md
  link.target = link.target:gsub('%.%/(.+%.md)', 'https://git-iit.fh-joanneum.at/omd/droid-devel/-/tree/main/%1')

  return link
end

-- TODO
-- Fix image sizes
-- It does NOT look nice, if we add {#id .class width=30 height=20px} for each of the images
-- better we set the dpi (ppi) per image with: ...