package main

import (
	"encoding/json"
	"os"
	"strings"
)

const (
	originDir  = "../src/main/resources/data/esoterica-origins/origins/"
	powerDir   = "../src/main/resources/data/esoterica-origins/powers/"
	outputFile = "./origins.md"
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
	data, _ := os.ReadFile(path)
	var object map[string]interface{}
	json.Unmarshal(data, &object)
	powerNames := object["powers"].([]interface{})
	powers := make([]Power, len(powerNames))
	for i, identifier := range powerNames {
		path := strings.Split(identifier.(string), ":")[1]
		powers[i] = parsePower(powerDir + path)
	}
	return Origin{
		name:        object["name"].(string),
		description: object["description"].(string),
		powers:      powers,
	}
}

type Power struct {
	name        string
	description string
}

func parsePower(path string) Power {
	data, _ := os.ReadFile(path)
	var object map[string]interface{}
	json.Unmarshal(data, &object)
	return Power{
		name:        object["name"].(string),
		description: object["description"].(string),
	}
}

func buildMarkdown(origins []Origin) string {
	// TODO: create a markdown file string from all the names and descriptions
}
