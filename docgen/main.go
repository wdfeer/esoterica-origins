package main

import (
	"encoding/json"
	"os"
	"strings"
)

const (
	originDir       = "../src/main/resources/data/esoterica-origins/origins/"
	powerDir        = "../src/main/resources/data/esoterica-origins/powers/"
	originsLangFile = "./en_us.json"
	outputFile      = "./origins.md"
)

func main() {
	files, _ := os.ReadDir(originDir)

	origins := make([]Origin, len(files))
	for i, filename := range files {
		origins[i] = parseOrigin(originDir + filename.Name())
	}

	markdown := buildMarkdown(origins)
	os.WriteFile(outputFile, []byte(markdown), 0644)
}

type Origin struct {
	name        string
	description string
	powers      []Power
}

func parseOrigin(path string) Origin {
	object := readJson(path)

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
	powers := make([]Power, len(powerNames))
	for i, identifier := range powerNames {
		split := strings.Split(identifier.(string), ":")
		powers[i] = parsePower(split[0], split[1])
	}

	return Origin{
		name:        object["name"].(string),
		description: object["description"].(string),
		powers:      powers,
	}
}

type Power struct {
	hidden      bool
	name        string
	description string
}

func parsePower(namespace string, name string) Power {
	if namespace == "esoterica-origins" {
		path := powerDir + name + ".json"
		object := readJson(path)

		if object["hidden"] == true {
			return Power{
				hidden: true,
			}
		}

		if object["name"] == nil {
			panic("Failed reading power at\"" + path + "\", name not found!")
		}
		if object["description"] == nil {
			panic("Failed reading power at\"" + path + "\", description not found!")
		}

		return Power{
			name:        object["name"].(string),
			description: object["description"].(string),
		}
	} else {
		var object map[string]any
		{
			data, _ := os.ReadFile(originsLangFile)
			json.Unmarshal(data, &object)
		}

		nameKey := "power.origins." + name + ".name"
		descKey := "power.origins." + name + ".description"
		if object[nameKey] == nil || object[descKey] == nil {
			return Power{
				hidden: true,
			}
		}

		return Power{
			name:        object[nameKey].(string),
			description: object[descKey].(string),
		}
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

func buildMarkdown(origins []Origin) string {
	var str strings.Builder
	str.WriteString("# Origins\n")
	for _, origin := range origins {
		str.WriteString("## " + origin.name + "\n")
		str.WriteString(origin.description + "\n\n")
		for _, power := range origin.powers {
			if !power.hidden {
				str.WriteString("- " + power.name + ": " + power.description + "\n")
			}
		}
		str.WriteString("\n")
	}
	return str.String()
}
