package main

import (
	"encoding/json"
	"os"
	"strings"
)

const (
	originDir  = "../src/main/resources/data/esoterica-origins/origins/"
	powerDir   = "../src/main/resources/data/esoterica-origins/powers/"
	outputFile = "./en_us.json"
)

func main() {
	files, _ := os.ReadDir(originDir)

	origins := make([]Origin, len(files))
	for i, filename := range files {
		origins[i] = parseOrigin(filename.Name())
	}

	lang := buildLang(origins)
	bytes, _ := json.Marshal(lang)
	os.WriteFile(outputFile, bytes, 0644)

	deleteOld(origins)
}

type Origin struct {
	path        string
	name        string
	description string
	powers      []Power
}

func parseOrigin(path string) Origin {
	object := readJson(originDir + path)

	if object["name"] == nil {
		panic("Failed reading origin at\"" + path + "\", name not found!")
	}
	if object["description"] == nil {
		panic("Failed reading origin at\"" + path + "\", description not found!")
	}
	if object["powers"] == nil {
		panic("Failed reading origin at\"" + path + "\", powers not found!")
	}

	powerNames := object["powers"].([]any)
	powers := []Power{}
	for _, identifier := range powerNames {
		split := strings.Split(identifier.(string), ":")
		if split[0] == "esoterica-origins" {
			powers = append(powers, parsePower(split[1]))
		}
	}

	return Origin{
		name:        object["name"].(string),
		description: object["description"].(string),
		powers:      powers,
	}
}

type Power struct {
	path        string
	hidden      bool
	name        string
	description string
}

func parsePower(name string) Power {
	path := powerDir + name + ".json"
	object := readJson(path)

	if object["hidden"] == true {
		return Power{
			hidden: true,
			path:   name,
		}
	}

	if object["name"] == nil {
		panic("Failed reading power at\"" + path + "\", name not found!")
	}
	if object["description"] == nil {
		panic("Failed reading power at\"" + path + "\", description not found!")
	}

	return Power{
		path:        name,
		name:        object["name"].(string),
		description: object["description"].(string),
	}
}

func readJson(path string) map[string]any {
	var object map[string]any
	data, err := os.ReadFile(path)
	if err != nil {
		panic("Failed to read file at \"" + path + "\":" + err.Error())
	}
	err = json.Unmarshal(data, &object)
	if err != nil {
		panic("Failed to unmarshal json at \"" + path + "\":" + err.Error())
	}
	return object
}

func buildLang(origins []Origin) map[string]string {
	dict := map[string]string{}
	for _, o := range origins {
		dict["origin.esoterica-origins."+o.path+".name"] = o.name
		dict["origin.esoterica-origins."+o.path+".description"] = o.description

		for _, p := range o.powers {
			if !p.hidden {
				dict["power.esoterica-origins."+p.path+".name"] = p.name
				dict["power.esoterica-origins."+p.path+".description"] = p.description
			}
		}
	}
	return dict
}

func deleteOld(origins []Origin) {
	files := make([]string, len(origins)*3)

	for _, origin := range origins {
		files = append(files, originDir+origin.path+".json")
		for _, power := range origin.powers {
			if !power.hidden {
				files = append(files, powerDir+power.path+".json")
			}
		}
	}

	for _, path := range files {
		data, err := os.ReadFile(path)
		if err != nil {
			panic("Error reading file whilst deleting old names! " + err.Error())
		}
		str := string(data)

		lines := strings.SplitAfter(str, "\n")
		newLines := make([]string, len(lines)/2)

		for _, line := range lines {
			// TODO: remove trailing comma before "name" if it was the last keyvalue
			if !strings.Contains(line, "\"name\"") && !strings.Contains(line, "\"description\"") {
				newLines = append(newLines, line)
			}
		}

		newStr := strings.Join(newLines, "")
		os.WriteFile(path, []byte(newStr), 0644)
	}
}
